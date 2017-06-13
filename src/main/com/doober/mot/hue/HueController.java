package com.doober.mot.hue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.doober.mot.MotMod;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLight.PHLightAlertMode;
import com.philips.lighting.model.PHLight.PHLightEffectMode;
import com.philips.lighting.model.PHLightState;

public class HueController implements PHSDKListener, PHLightListener {
	
	public PHHueSDK phHueSDK;
	public List<PHAccessPoint> accessPoints = new ArrayList<PHAccessPoint>();
	
	public HueController () {
		phHueSDK = PHHueSDK.getInstance();
		phHueSDK.setAppName(MotMod.name);
		System.out.println("Initialized Hue SDK");
	}

	public void set(String lightID, PHLightState state) {
		List<PHBridge> bridges = phHueSDK.getAllBridges();
        if (bridges.isEmpty()) {
        	System.out.println("HUE: No bridges");
            return;
		} else {
	        for (PHBridge bridge : bridges) {
	        	Map<String, PHLight> lights = bridge.getResourceCache().getLights();
	        	System.out.println("HUE: lights set: "+lights.keySet().toString());
	            if (lights.containsKey(lightID)) {
	            	bridge.updateLightState(lights.get(lightID),  state, this);
	            	System.out.println("HUE: set: "+state.toString());
	            }
	        }
		}
	}
	
	public List<PHLight> getLights() {
		List<PHLight> lights = new ArrayList<PHLight>();
		List<PHBridge> bridges = phHueSDK.getAllBridges();
        if (bridges.isEmpty()) {
        	System.out.println("HUE: No bridges");
		} else {
	        for (PHBridge bridge : bridges) {
	            lights.addAll(bridge.getResourceCache().getAllLights());
	        }
		}
        //System.out.println("HUE: lights: "+lights.toString());
        return lights;
	}
        
    public void allOn() {
        List<PHBridge> bridges = phHueSDK.getAllBridges();
        System.out.println("HUE: Setting all lights on...");
        if (bridges.isEmpty()) {
        	System.out.println("HUE: No bridges");
            return;
        }
        for (PHBridge bridge : bridges) {
            for (PHLight light : bridge.getResourceCache().getAllLights()) {
                PHLightState lightState = new PHLightState();
                float[] xy = PHUtilities.calculateXYFromRGB(254, 254, 254, light.getModelNumber());
                lightState.setOn(true);
                lightState.setBrightness(254);
                lightState.setX(xy[0]);
                lightState.setY(xy[1]);
                bridge.updateLightState(light, lightState, this);
            }
        }
    }

    public void pair(String id) {
        for (PHAccessPoint accessPoint : this.accessPoints) {
            if (accessPoint.getBridgeId().endsWith(id)) {
                phHueSDK.connect(accessPoint);
            }
        }
    }
    
    public void start() {
        this.phHueSDK.getNotificationManager().registerSDKListener(this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }
    
    public void search() {
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);    	
    }
    
	@Override
	public void onAccessPointsFound(List<PHAccessPoint> list) {
		for (PHAccessPoint ap : list) {
			System.out.println("HUE: SDK got AP "+ap.getIpAddress());
			phHueSDK.connect(ap);
		}
		accessPoints.addAll(list);
	}

	@Override
	public void onAuthenticationRequired(PHAccessPoint accessPoint) {
		System.out.println("HUE: Press button on "+accessPoint.getBridgeId());
		phHueSDK.startPushlinkAuthentication(accessPoint);
	}

	@Override
	public void onBridgeConnected(PHBridge bridge, String bridgeString) {
		System.out.print("HUE: SDK connected to "+bridgeString);
		List<PHLight> lights = getLights();
	}

	@Override
	public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionLost(PHAccessPoint accessPoint) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionResumed(PHBridge bridge) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(int arg0, String error) {
		System.out.println("Hue SDK error:"+error);
		
	}

	@Override
	public void onParsingErrors(List<PHHueParsingError> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess() {
		System.out.println("HUE: success with something");
		
	}

	@Override
	public void onReceivingLightDetails(PHLight arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceivingLights(List<PHBridgeResource> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchComplete() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
