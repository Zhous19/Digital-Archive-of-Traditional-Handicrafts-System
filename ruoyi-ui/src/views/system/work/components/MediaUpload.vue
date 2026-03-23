<template>
  <div class="media-upload">
    <el-form label-width="90px" size="small">
      <el-form-item label="上传类型">
        <el-radio-group v-model="uploadType" @change="handleTypeChange">
          <el-radio label="image">上传图片</el-radio>
          <el-radio label="video">上传视频</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="uploadType === 'image' ? '作品图片' : '作品视频'">
        <el-upload
          ref="mediaUpload"
          :action="uploadAction"
          :accept="acceptType"
          :auto-upload="false"
          :limit="1"
          :file-list="fileList"
          :on-change="handleChange"
          :http-request="handleCustomUpload"
          :before-upload="handleBeforeUpload"
          :on-exceed="handleExceed"
          :on-remove="handleRemove"
          list-type="picture-card"
          :class="{ hide: fileList.length >= 1 }"
        >
          <i class="el-icon-plus" />
        </el-upload>
        <el-button
          size="mini"
          type="primary"
          style="margin-top: 8px"
          :disabled="!fileList.length || disabled"
          @click="handleConfirmUpload"
        >
          确认上传
        </el-button>
        <div class="el-upload__tip" v-if="!disabled">
          <template v-if="uploadType === 'image'">
            仅支持 jpg、png、jpeg，大小不超过 <b style="color: #f56c6c">{{ imageSize }}MB</b>
          </template>
          <template v-else>
            仅支持 mp4、webm 等视频格式，大小不超过 <b style="color: #f56c6c">{{ videoSize }}MB</b>
          </template>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { uploadWorkImage, uploadWorkVideo } from "@/api/system/work"

export default {
  name: "MediaUpload",
  props: {
    // 作品ID（后端根据该ID关联 work 表记录）
    workId: {
      // 允许为 null，由内部上传逻辑再次校验
      default: null
    },
    // 禁用（仅展示）
    disabled: {
      type: Boolean,
      default: false
    },
    // 图片大小限制(MB)
    imageSize: {
      type: Number,
      default: 5
    },
    // 视频大小限制(MB)
    videoSize: {
      type: Number,
      default: 50
    }
  },
  data() {
    return {
      uploadType: "image",
      fileList: [],
      uploadAction: "#"
    }
  },
  computed: {
    acceptType() {
      return this.uploadType === "image"
        ? "image/jpeg,image/png,image/jpg"
        : "video/mp4,video/webm"
    }
  },
  methods: {
    handleChange(file, fileList) {
      // 选中文件时，同步到本地 fileList，用于控制“确认上传”按钮是否可点
      this.fileList = fileList
    },
    handleConfirmUpload() {
      if (!this.fileList.length) {
        this.$modal.msgError("请先选择要上传的文件")
        return
      }
      if (this.disabled) {
        return
      }
      this.$refs.mediaUpload && this.$refs.mediaUpload.submit()
    },
    handleTypeChange() {
      this.fileList = []
      this.$emit("input", null)
      this.$emit("change", { type: this.uploadType, url: null })
    },
    handleBeforeUpload(file) {
      if (this.uploadType === "image") {
        const isImg = ["image/jpeg", "image/png", "image/jpg"].some(
          t => file.type === t
        )
        if (!isImg) {
          this.$modal.msgError("请上传 jpg、png、jpeg 格式的图片")
          return false
        }
        const isLt = file.size / 1024 / 1024 < this.imageSize
        if (!isLt) {
          this.$modal.msgError(`图片大小不能超过 ${this.imageSize} MB`)
          return false
        }
      } else {
        const isVideo = ["video/mp4", "video/webm"].some(t => file.type === t)
        if (!isVideo) {
          this.$modal.msgError("请上传 mp4、webm 格式的视频")
          return false
        }
        const isLt = file.size / 1024 / 1024 < this.videoSize
        if (!isLt) {
          this.$modal.msgError(`视频大小不能超过 ${this.videoSize} MB`)
          return false
        }
      }
      if (file.name.includes(",")) {
        this.$modal.msgError("文件名不能包含英文逗号")
        return false
      }
      this.$modal.loading("正在上传，请稍候...")
      return true
    },
    handleExceed() {
      this.$modal.msgError("只能上传一个文件，请先删除当前文件再上传")
    },
    handleCustomUpload({ file }) {
      const id = this.workId
      if (!id && id !== 0) {
        this.$modal.closeLoading()
        this.$modal.msgError("请先保存作品档案，再上传图片/视频")
        return
      }
      const api = this.uploadType === "image" ? uploadWorkImage : uploadWorkVideo
      api(id, file)
        .then(res => {
          this.$modal.closeLoading()
          const url = res.url || res.data?.url || res?.data
          if (!url) {
            this.$modal.msgError("上传成功但未返回访问地址")
            return
          }
          this.fileList = [{ name: file.name, url }]
          this.$emit("input", url)
          this.$emit("change", { type: this.uploadType, url })
          this.$emit("success", { type: this.uploadType, url, file })
        })
        .catch(() => {
          this.$modal.closeLoading()
        })
    },
    handleRemove() {
      this.fileList = []
      this.$emit("input", null)
      this.$emit("change", { type: this.uploadType, url: null })
    },
    // 供父组件调用：清空已选
    clear() {
      this.fileList = []
      this.uploadType = "image"
      this.$refs.mediaUpload && this.$refs.mediaUpload.clearFiles()
    }
  }
}
</script>

<style scoped lang="scss">
.media-upload {
  padding: 0 4px;
}
::v-deep .hide .el-upload--picture-card {
  display: none;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  width: 100px;
  height: 100px;
}
</style>
