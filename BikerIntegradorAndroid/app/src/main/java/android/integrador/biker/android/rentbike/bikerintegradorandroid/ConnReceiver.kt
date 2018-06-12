package android.integrador.biker.android.rentbike.bikerintegradorandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

/**
 * Created by João Paulo on 10/06/2018.
 */
class ConnReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var cm: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

        var netInfo = cm.activeNetworkInfo

        if(netInfo != null && netInfo.isConnectedOrConnecting){
            Toast.makeText(context, "Conectado à Internet ", Toast.LENGTH_LONG).show()
            Log.i("BRTESTE","CONECTADO!!!")
        }else{
            Toast.makeText(context, "Sem acesso à Internet ", Toast.LENGTH_LONG).show()
            Log.i("BRTESTE","DESCONECTADO!!!")
        }

    }
}