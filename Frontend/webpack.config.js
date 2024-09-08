const path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  entry: './src/index.ts',
  mode: 'development',

  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },

  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },

  plugins: [
    new CopyWebpackPlugin({
      patterns: [
        {
          from: '**/*.html',
          context: path.resolve(__dirname, 'src/views'),
          to: path.resolve(__dirname, 'dist/[path][name][ext]'),
        },
        {
          from: '**/*.css',
          context: path.resolve(__dirname, 'src/views'),
          to: path.resolve(__dirname, 'dist/[path][name][ext]'),
        },
      ],
    }),
  ],

  devServer: {
    static: {
      directory: path.join(__dirname, 'dist'),
      watch: true,
    },
    compress: true,
    port: 9000,
    hot: true,
  },

  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
};