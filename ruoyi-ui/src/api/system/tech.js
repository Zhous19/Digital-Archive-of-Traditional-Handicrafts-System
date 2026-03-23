import request from '@/utils/request'

// 查询技艺档案列表
export function listTech(query) {
  return request({
    url: '/system/tech/list',
    method: 'get',
    params: query
  })
}

// 查询技艺档案详细
export function getTech(id) {
  return request({
    url: '/system/tech/' + id,
    method: 'get'
  })
}

// 新增技艺档案
export function addTech(data) {
  return request({
    url: '/system/tech',
    method: 'post',
    data: data
  })
}

// 修改技艺档案
export function updateTech(data) {
  return request({
    url: '/system/tech',
    method: 'put',
    data: data
  })
}

// 删除技艺档案
export function delTech(id) {
  return request({
    url: '/system/tech/' + id,
    method: 'delete'
  })
}
