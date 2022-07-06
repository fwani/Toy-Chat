const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    outputDir: "../src/main/resources/static",
    indexPath: "index.html",
    devServer: {
        port: 7070,
        proxy: {
            '*': {
                target: 'http://localhost:8080'
            }
        },
    },
    chainWebpack: config => {
        const svgRule = config.module.rule("svg");
        svgRule.uses.clear();
        svgRule.use("vue-svg-loader").loader("vue-svg-loader");
    }
})
