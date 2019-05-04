package com.mkiisoft.iowallet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.postDelayed
import com.airbnb.lottie.LottieAnimationView
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

class CountdownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val root: View = LayoutInflater.from(context).inflate(R.layout.countdown, this, true)
    private var days1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_days_1) }
    private var days2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_days_2) }
    private var hours1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_hours_1) }
    private var hours2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_hours_2) }
    private var mins1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_mins_1) }
    private var mins2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_mins_2) }
    private var secs1 by AnimateDigitDelegate { root.findViewById(R.id.countdown_secs_1) }
    private var secs2 by AnimateDigitDelegate { root.findViewById(R.id.countdown_secs_2) }

    private val updateTime: Runnable = object : Runnable {

        private val conferenceStart = TimeUtils.ConferenceDays.first().start.plusHours(3L)

        override fun run() {
            var timeUntilConf = Duration.between(ZonedDateTime.now(), conferenceStart)

            if (timeUntilConf.isNegative) {
                return
            }

            val days = timeUntilConf.toDays()
            days1 = (days / 10).toInt()
            days2 = (days % 10).toInt()
            timeUntilConf = timeUntilConf.minusDays(days)

            val hours = timeUntilConf.toHours()
            hours1 = (hours / 10).toInt()
            hours2 = (hours % 10).toInt()
            timeUntilConf = timeUntilConf.minusHours(hours)

            val mins = timeUntilConf.toMinutes()
            mins1 = (mins / 10).toInt()
            mins2 = (mins % 10).toInt()
            timeUntilConf = timeUntilConf.minusMinutes(mins)

            val secs = timeUntilConf.seconds
            secs1 = (secs / 10).toInt()
            secs2 = (secs % 10).toInt()

            handler?.postDelayed(this, 1_000L)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handler?.post(updateTime)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler?.removeCallbacks(updateTime)
    }

    private class AnimateDigitDelegate(
        private val viewProvider: () -> LottieAnimationView
    ) : ObservableProperty<Int>(-1) {
        override fun afterChange(property: KProperty<*>, oldValue: Int, newValue: Int) {
            if (newValue < 0 || newValue > 9) {
                return
            }

            if (oldValue != newValue) {
                val view = viewProvider()
                if (oldValue != -1) {
                    view.setAnimation("anim/$oldValue.json")
                    view.setMinAndMaxProgress(0.5f, 1f)
                    view.speed = 1.1f
                    view.playAnimation()

                    view.postDelayed(500L) {
                        view.setAnimation("anim/$newValue.json")
                        view.setMinAndMaxProgress(0f, 0.5f)
                        view.speed = 1f
                        view.playAnimation()
                    }
                } else {
                    view.setAnimation("anim/$newValue.json")
                    view.setMinAndMaxProgress(0f, 0.5f)
                    view.playAnimation()
                }
            }
        }
    }
}