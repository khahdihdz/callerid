package com.khahdihdz.callerid.platform

import android.telecom.Call
import android.telecom.CallScreeningService

/** Safe initial integration: unknown numbers are never blocked automatically. */
class CallerScreeningService : CallScreeningService() {
    override fun onScreenCall(callDetails: Call.Details) {
        val response = CallResponse.Builder()
            .setDisallowCall(false)
            .setRejectCall(false)
            .setSkipCallLog(false)
            .setSkipNotification(false)
            .build()
        respondToCall(callDetails, response)
    }
}
