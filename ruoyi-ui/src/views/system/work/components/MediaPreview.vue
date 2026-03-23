<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="800px"
    append-to-body
    class="media-preview-dialog"
    @close="handleClose"
  >
    <div class="media-preview-body" v-if="resolvedUrl">
      <div v-if="actualMediaType === 'image'" class="preview-wrap preview-image">
        <img
          :src="resolvedUrl"
          alt="作品图片"
          class="preview-media"
          referrerpolicy="no-referrer"
          @error="onMediaError"
        />
        <div v-if="loadError" class="media-load-error">
          <i class="el-icon-warning-outline"></i>
          <p>图片加载失败</p>
          <p class="tip">请检查：① MinIO 桶是否允许匿名读 ② 地址是否可访问</p>
        </div>
      </div>
      <div v-else-if="actualMediaType === 'video'" class="preview-wrap preview-video">
        <video
          :src="resolvedUrl"
          controls
          class="preview-media"
          preload="metadata"
          referrerpolicy="no-referrer"
          @error="onMediaError"
        />
        <div v-if="loadError" class="media-load-error">
          <i class="el-icon-warning-outline"></i>
          <p>视频加载失败</p>
          <p class="tip">请检查：① MinIO 桶是否允许匿名读 ② 地址是否可访问</p>
        </div>
      </div>
    </div>
    <div class="media-preview-empty" v-else>
      <i class="el-icon-picture-outline"></i>
      <span>暂无预览地址</span>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" @click="handleClose">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
/**
 * 作品图片/视频预览弹窗，适配 Minio 等完整 URL（如 http://154.21.200.11:9000/shuyun/...）
 */
const IMAGE_EXTS = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp', 'svg']
const VIDEO_EXTS = ['mp4', 'webm', 'ogg', 'm4v', 'mov']

export default {
  name: "MediaPreview",
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    // 资源地址：支持完整 URL 或相对路径（相对路径时使用 baseUrl 拼接）
    url: {
      type: String,
      default: ""
    },
    // image | video
    mediaType: {
      type: String,
      default: "image"
    },
    // 相对路径时的基础地址（如 Minio 地址），完整 URL 时不拼接
    baseUrl: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      loadError: false
    }
  },
  watch: {
    fullUrl() {
      this.loadError = false
    }
  },
  computed: {
    dialogVisible: {
      get() {
        return this.visible
      },
      set(val) {
        this.$emit("update:visible", val)
      }
    },
    // 统一解析最终预览地址：优先使用传入的 url，支持完整 URL 或相对路径 + baseUrl
    resolvedUrl() {
      const u = (this.url || "").trim()
      if (!u) return ""
      if (/^https?:\/\//i.test(u)) return u
      const base = (this.baseUrl || "").replace(/\/$/, "")
      return base ? base + (u.startsWith("/") ? u : "/" + u) : u
    },
    // 从 URL 中解析后缀（去掉查询参数和锚点）
    ext() {
      const cleanUrl = this.resolvedUrl.split("?")[0].split("#")[0]
      const segments = cleanUrl.split(".")
      if (segments.length < 2) return ""
      return segments.pop().toLowerCase()
    },
    // 实际预览类型：优先使用外部传入的 mediaType，其次根据后缀自动判断
    actualMediaType() {
      if (this.mediaType === "image" || this.mediaType === "video") {
        return this.mediaType
      }
      if (IMAGE_EXTS.includes(this.ext)) return "image"
      if (VIDEO_EXTS.includes(this.ext)) return "video"
      return ""
    },
    // 兼容旧用法：保留 fullUrl 计算属性
    fullUrl() {
      return this.resolvedUrl
    },
    dialogTitle() {
      return this.actualMediaType === "video" ? "作品视频预览" : "作品图片预览"
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.loadError = false
      this.$emit("close")
    },
    onMediaError() {
      this.loadError = true
    }
  }
}
</script>

<style scoped lang="scss">
.media-preview-dialog {
  .media-preview-body {
    text-align: center;
    min-height: 200px;
    background: #f5f7fa;
  }
  .preview-wrap {
    display: inline-block;
    max-width: 100%;
  }
  .preview-image .preview-media {
    max-width: 100%;
    max-height: 70vh;
    display: block;
    margin: 0 auto;
  }
  .preview-video .preview-media {
    max-width: 100%;
    width: 100%;
    max-height: 70vh;
  }
  .media-preview-empty {
    min-height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #909399;
    font-size: 14px;
    background: #f5f7fa;
    i {
      font-size: 48px;
      margin-right: 8px;
    }
  }
  .media-load-error {
    margin-top: 12px;
    color: #e6a23c;
    font-size: 14px;
    i {
      font-size: 24px;
      margin-right: 6px;
    }
    .tip {
      margin-top: 8px;
      color: #909399;
      font-size: 12px;
    }
  }
}
</style>
