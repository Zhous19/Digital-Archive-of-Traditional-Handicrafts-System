<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作品名称" prop="workName">
        <el-input
          v-model="queryParams.workName"
          placeholder="请输入作品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属品类ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入所属品类ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="匠人ID" prop="artistId">
        <el-input
          v-model="queryParams.artistId"
          placeholder="请输入匠人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:work:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:work:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:work:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:work:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="作品名称" align="center" prop="workName" />
      <el-table-column label="所属品类ID" align="center" prop="categoryId" />
      <el-table-column label="匠人ID" align="center" prop="artistId" />
      <el-table-column label="作品图片" align="center" prop="workPic" min-width="120">
        <template slot-scope="scope">
          <el-link
            v-if="scope.row.workPic"
            type="primary"
            :underline="false"
            @click="openPreview(scope.row.workPic, 'image')"
          >预览</el-link>
          <span v-else class="text-muted">—</span>
        </template>
      </el-table-column>
      <el-table-column label="作品视频" align="center" prop="workVideo" min-width="120">
        <template slot-scope="scope">
          <el-link
            v-if="scope.row.workVideo"
            type="primary"
            :underline="false"
            @click="openPreview(scope.row.workVideo, 'video')"
          >预览</el-link>
          <span v-else class="text-muted">—</span>
        </template>
      </el-table-column>
      <el-table-column label="创作背景" align="center" prop="createBg" />
      <el-table-column label="文化内涵" align="center" prop="culturalConnotation" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload2"
            @click="handleUploadOpen(scope.row)"
          >上传</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:work:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:work:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改作品档案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" @close="cancel" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作品名称" prop="workName">
          <el-input v-model="form.workName" placeholder="请输入作品名称" />
        </el-form-item>
        <el-form-item label="所属品类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入所属品类ID" />
        </el-form-item>
        <el-form-item label="匠人ID" prop="artistId">
          <el-input v-model="form.artistId" placeholder="请输入匠人ID" />
        </el-form-item>
        <el-form-item label="作品图片URL" prop="workPic">
          <el-input v-model="form.workPic" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="作品视频URL" prop="workVideo">
          <el-input v-model="form.workVideo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创作背景" prop="createBg">
          <el-input v-model="form.createBg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="文化内涵" prop="culturalConnotation">
          <el-input v-model="form.culturalConnotation" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 作品图片/视频预览 -->
    <media-preview
      :visible.sync="previewVisible"
      :url="previewUrl"
      :media-type="previewType"
      :base-url="previewBaseUrl"
    />

    <!-- 上传作品图片/视频对话框 -->
    <el-dialog
      title="上传作品素材"
      :visible.sync="uploadOpen"
      width="520px"
      append-to-body
      @close="handleUploadClose"
    >
      <media-upload
        v-if="uploadWorkId !== null"
        ref="mediaUpload"
        :work-id="uploadWorkId"
        @success="handleUploadSuccess"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleUploadClose">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWork, getWork, delWork, addWork, updateWork } from "@/api/system/work"
import MediaUpload from "./components/MediaUpload"
import MediaPreview from "./components/MediaPreview"

export default {
  name: "Work",
  components: { MediaUpload, MediaPreview },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 作品档案表格数据
      workList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 上传弹窗
      uploadOpen: false,
      // 当前上传的作品ID
      uploadWorkId: null,
      // 预览弹窗（适配 Minio 等完整 URL，相对路径时可配置 baseUrl）
      previewVisible: false,
      previewUrl: "",
      previewType: "image",
      previewBaseUrl: "", // 相对路径时填写，如 "http://154.21.200.11:9000"
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workName: null,
        categoryId: null,
        artistId: null,
        workPic: null,
        workVideo: null,
        createBg: null,
        culturalConnotation: null,
      },
      // 表单参数
      form: {},
       
      // 表单校验
      rules: {
        workName: [
          { required: true, message: "作品名称不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "所属品类ID不能为空", trigger: "blur" }
        ],
        artistId: [
          { required: true, message: "匠人ID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询作品档案列表 */
    getList() {
      this.loading = true
      listWork(this.queryParams).then(response => {
        this.workList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        workName: null,
        categoryId: null,
        artistId: null,
        workPic: null,
        workVideo: null,
        createBg: null,
        culturalConnotation: null,
        createTime: null,
        updateTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加作品档案"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWork(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改作品档案"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWork(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除作品档案编号为"' + ids + '"的数据项？').then(function() {
        return delWork(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/work/export', {
        ...this.queryParams
      }, `work_${new Date().getTime()}.xlsx`)
    },
    /** 打开上传弹窗 */
    handleUploadOpen(row) {
      if (!row || !row.id) {
        this.$modal.msgError("请先保存作品档案，再上传图片/视频")
        return
      }
      this.uploadWorkId = row.id
      this.uploadOpen = true
    },
    /** 关闭上传弹窗 */
    handleUploadClose() {
      this.uploadOpen = false
      this.uploadWorkId = null
      this.$refs.mediaUpload && this.$refs.mediaUpload.clear()
    },
    /** 上传成功回调：提示并刷新列表 */
    handleUploadSuccess({ type, url }) {
      this.$modal.msgSuccess(type === "image" ? "图片上传成功" : "视频上传成功")
      this.handleUploadClose()
      this.getList()
    },
    /** 打开图片/视频预览（支持 Minio 完整 URL） */
    openPreview(url, type) {
      this.previewUrl = url || ""
      this.previewType = type || "image"
      this.previewVisible = true
    },
  }
    
  
}
</script>
