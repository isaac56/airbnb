const { createProxyMiddleware } = require('http-proxy-middleware');
const cors = require('cors');

module.exports = function (app) {
  app.use(
    cors(
      '/api',
      createProxyMiddleware({
        target: 'http://3.35.85.246:8080/',
        changeOrigin: true,
      })
    )
  );
};
