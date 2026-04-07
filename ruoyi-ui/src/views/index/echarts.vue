<template>
  <div class="handcraft-dashboard" style="padding: 20px;">

    <!-- 1. 图片画廊 -->
    <div class="gallery" style="margin-bottom:20px;">
      <el-carousel height="320px" :interval="4000" arrow="always">
        <el-carousel-item v-for="(image, index) in workImages" :key="index">
          <img :src="image.workPic" :alt="image.workName" style="width:100%;height:100%;object-fit:cover;border-radius:8px;">
        </el-carousel-item>
        <!-- 默认图片 -->
        <el-carousel-item v-if="workImages.length === 0">
          <img src="https://picsum.photos/1200/320?random=1" style="width:100%;height:100%;object-fit:cover;border-radius:8px;">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="display:flex;gap:20px;margin-bottom:20px;">
      <!-- 2. 作品品类饼图 -->
      <div class="panel" style="flex:1;background:#fff;padding:20px;border-radius:8px;">
        <h3>作品品类分布</h3>
        <div ref="pie" style="width:100%;height:320px;"></div>
      </div>

      <!-- 3. 地域分布地图 -->
      <div class="panel" style="flex:1;background:#fff;padding:20px;border-radius:8px;">
        <h3>手工艺地域分布</h3>
        <div ref="map" style="width:100%;height:320px;"></div>
      </div>
    </div>

    <div style="display:flex;gap:20px;">
      <!-- 4. 技艺流程图 -->
      <div class="panel" style="flex:1;background:#fff;padding:20px;border-radius:8px;">
        <h3>技艺传承流程</h3>
        <div ref="flow" style="width:100%;height:300px;"></div>
      </div>

      <!-- 5. 视频播放 -->
      <div class="panel" style="flex:1;background:#fff;padding:20px;border-radius:8px;">
        <h3>手工艺技艺视频</h3>
        <video controls style="width:100%;height:260px;object-fit:cover;border-radius:6px;margin-top:10px;">
          <source src="https://www.w3school.com.cn/i/movie.mp4" type="video/mp4">
        </video>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'HandcraftDashboard',
  data() {
    return {
      pieChart: null,
      mapChart: null,
      flowChart: null,
      statData: null, // 后端统计数据
      workImages: [] // 作品图片列表
    }
  },
  mounted() {
    this.initCharts()
    this.loadDataFromBackend()
  },
  methods: {
    // 初始化所有图表
    initCharts() {
      this.pieChart = this.$echarts.init(this.$refs.pie)
      this.mapChart = this.$echarts.init(this.$refs.map)
      this.flowChart = this.$echarts.init(this.$refs.flow)
      this.initFlowChart()
    },

    // 加载后端真实数据
    async loadDataFromBackend() {
      try {
        const res = await this.$http.get('/handcraft/stat/getData')
        if (res.code === 200) {
          this.statData = res.data
          this.workImages = res.data.workImages || []
          this.updatePieChart() // 更新饼图
          this.updateMapChart() // 更新地图
        }
      } catch (err) {
        console.error("数据加载失败，使用模拟数据", err)
        // 使用模拟数据
        this.statData = {
            categoryNames: ['陶瓷', '刺绣', '木雕', '竹编', '剪纸'],
            categoryCounts: [25, 18, 15, 12, 10],
            regionNames: ['云南', '贵州', '四川', '江苏', '浙江'],
            regionCounts: [12, 9, 15, 11, 8],
            workImages: []
        }
        this.workImages = this.statData.workImages
        this.updatePieChart() // 更新饼图
        this.updateMapChart() // 更新地图
      }
    },

    // 更新品类饼图（后端数据）
    updatePieChart() {
      const data = this.statData.categoryNames.map((name, index) => ({
        name,
        value: this.statData.categoryCounts[index]
      }))
      this.pieChart.setOption({
        tooltip: { trigger: 'item' },
        series: [{
          type: 'pie', radius: '60%', data
        }]
      })
    },

    // 更新地图（后端地域数据）
        updateMapChart() {
            // 由于ECharts 5.x版本不再内置地图数据，这里使用饼图替代
            const data = this.statData.regionNames.map((name, index) => ({
                name,
                value: this.statData.regionCounts[index]
            }))
            this.mapChart.setOption({
                tooltip: { trigger: 'item' },
                series: [{
                    type: 'pie', radius: '60%', data
                }]
            })
        },

    // 技艺流程图
    initFlowChart() {
      this.flowChart.setOption({
        series: [{
          type: 'graph', layout: 'force',
          data: [
            { name: '原料采集' },
            { name: '传统工艺' },
            { name: '手工制作' },
            { name: '精细打磨' },
            { name: '成品展示' }
          ],
          links: [
            { source: '原料采集', target: '传统工艺' },
            { source: '传统工艺', target: '手工制作' },
            { source: '手工制作', target: '精细打磨' },
            { source: '精细打磨', target: '成品展示' }
          ]
        }]
      })
    }
  }
}
</script>