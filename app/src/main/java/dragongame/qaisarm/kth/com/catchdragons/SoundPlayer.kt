package dragongame.qaisarm.kth.com.catchdragons

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build


@Suppress("DEPRECATION")
class SoundPlayer(context: Context) {

    companion object {
        private var soundPool: SoundPool? = null
        private var hitSound: Int = 0
        private var overSound: Int = 0
        private const val SOUND_POOL_MAX = 2
    }
    private var audioAttributes: AudioAttributes? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            soundPool = SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build()
        } else {
            soundPool = SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0)
        }
        hitSound = soundPool !!.load(context, R.raw.hit2, 1)
        overSound = soundPool !!.load(context, R.raw.end, 1)
    }

    fun playHitSound() {
        soundPool !!.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }
    fun playOverSound() {
        soundPool !!.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }
}
