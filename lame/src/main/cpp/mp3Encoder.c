//
// Created by HWilliam on 2020-01-20.
//


#include "mp3Encoder.h"
#include "libmp3lame/lame.h"
#include <stdio.h>
#include <stdlib.h>

#define BUFFER_SIZE (1024*256)


FILE *pcmFile = NULL;
FILE *mp3File = NULL;
lame_t lameClient = NULL;

int init(const char *pcmFilePath, const char *mp3FilePath,
         int sampleRate, int channels, int bitrate) {
    int ret = -1;
    pcmFile = fopen(pcmFilePath, "rb");
    if (pcmFile) {
        mp3File = fopen(mp3FilePath, "wb");
        if (mp3File) {
            lameClient = lame_init();
            lame_set_in_samplerate(lameClient, sampleRate);
            lame_set_out_samplerate(lameClient, sampleRate);
            lame_set_num_channels(lameClient, channels);
            lame_set_brate(lameClient, bitrate / 1000);
            lame_init_params(lameClient);
            ret = 0;
        }
    }
    return ret;
}

void encode() {
    if (pcmFile == NULL) {

    }
    int bufferSize = BUFFER_SIZE;
    short *buffer = (short *) malloc(bufferSize / 2 * sizeof(short));
    short *leftBuffer = malloc(bufferSize / 4 * sizeof(short));
    short *rightBuffer = malloc(bufferSize / 4 * sizeof(short));
    unsigned char *mp3_buffer = malloc(bufferSize * sizeof(unsigned char));

    size_t readBufferSize = 0;
    while ((readBufferSize = fread(buffer, 2, (size_t) bufferSize / 2, pcmFile)) > 0) {
        //将buffer中的数据分别分配到左声道和右声道去
        for (int i = 0; i < readBufferSize; ++i) {
            if (i % 2 == 0) {
                leftBuffer[i / 2] = buffer[i];
            } else {
                rightBuffer[i / 2] = buffer[i];
            }
        }

        size_t wroteSize = (size_t) lame_encode_buffer(lameClient, leftBuffer, rightBuffer,
                                                       readBufferSize / 2,
                                                       mp3_buffer, bufferSize);
        fwrite(mp3_buffer, 1, wroteSize, mp3File);
    }
}

void destroy() {
    if (pcmFile) {
        fclose(pcmFile);
        pcmFile = NULL;
    }
    if (mp3File) {
        fclose(mp3File);

        mp3File = NULL;
    }
    if (lameClient) {
        lame_close(lameClient);
        lameClient = NULL;
    }
}