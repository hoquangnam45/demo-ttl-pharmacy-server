package com.hoquangnam45.ttl.pharmacy.server.di;

import javax.xml.ws.WebServiceFeature;
import com.sun.xml.ws.api.FeatureConstructor;

public class GuiceManagedFeature extends WebServiceFeature {
  public static final String ID = "FEATURE_GuiceManaged";

  @FeatureConstructor
  public GuiceManagedFeature() {
    this.enabled = true;
  }

  @Override
  public String getID() {
    return ID;
  }
}
