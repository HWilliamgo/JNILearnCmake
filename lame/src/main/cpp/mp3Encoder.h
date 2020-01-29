//
// Created by HWilliam on 2020-01-20.
//

#ifndef JNILEARNCMAKE_MP3ENCODER_H
#define JNILEARNCMAKE_MP3ENCODER_H

#endif //JNILEARNCMAKE_MP3ENCODER_H

int init(const char *pcmFilePath, const char *mp3FilePath,
          int sampleRate, int channels, int bitrate);

void encode();

void destroy();
