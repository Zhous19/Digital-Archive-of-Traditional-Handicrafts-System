import request from '@/utils/request'

// 查询作品档案列表
export function listWork(query) {
  return request({
    url: '/system/work/list',
    method: 'get',
    params: query
  })
}

// 查询作品档案详细
export function getWork(id) {
  return request({
    url: '/system/work/' + id,
    method: 'get'
  })
}

// 新增作品档案
export function addWork(data) {
  return request({
    url: '/system/work',
    method: 'post',
    data: data
  })
}

// 修改作品档案
export function updateWork(data) {
  return request({
    url: '/system/work',
    method: 'put',
    data: data
  })
}

// 删除作品档案
export function delWork(id) {
  return request({
    url: '/system/work/' + id,
    method: 'delete'
  })
}

// 上传作品图片（返回 MinIO/本地访问 URL）
export function uploadWorkImage(id, file) {
  const formData = new FormData()
  formData.append('id', id)
  formData.append('file', file)
  return request({
    url: '/system/work/uploadImage',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

// 上传作品视频（返回 MinIO/本地访问 URL）
export function uploadWorkVideo(id, file) {
  const formData = new FormData()
  formData.append('id', id)
  formData.append('file', file)
  return request({
    url: '/system/work/uploadVideo',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
