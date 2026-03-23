import request from '@/utils/request'

// 查询匠人档案列表
export function listArtist(query) {
  return request({
    url: '/system/artist/list',
    method: 'get',
    params: query
  })
}

// 查询匠人档案详细
export function getArtist(id) {
  return request({
    url: '/system/artist/' + id,
    method: 'get'
  })
}

// 新增匠人档案
export function addArtist(data) {
  return request({
    url: '/system/artist',
    method: 'post',
    data: data
  })
}

// 修改匠人档案
export function updateArtist(data) {
  return request({
    url: '/system/artist',
    method: 'put',
    data: data
  })
}

// 删除匠人档案
export function delArtist(id) {
  return request({
    url: '/system/artist/' + id,
    method: 'delete'
  })
}
