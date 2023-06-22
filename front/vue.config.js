const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '^/sad':{
        target: 'http://localhost:8080/',
        ws: true,
        changeOrigin: true
      },
    }
  }
})
