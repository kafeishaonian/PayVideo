#version 120
attribute vec4 position;
attribute vec4 inputTextureCoordinate;

//纹理坐标
varying vec2 textureCoordinate;
//纹理变换
uniform mat4 textureTransform;

void main() {
    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;
    gl_Position = position;
}
