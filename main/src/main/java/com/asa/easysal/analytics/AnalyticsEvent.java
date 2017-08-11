package com.asa.easysal.analytics;

import android.support.annotation.Nullable;

import com.asa.easysal.analytics.enums.AdditionalData;
import com.asa.easysal.analytics.enums.EventName;

import java.util.HashMap;
import java.util.Map;

public class AnalyticsEvent {

    private Map<AdditionalData, Object> dataMap;
    private EventName eventName;

    public static Builder eventName(EventName eventName) {
        return new Builder(eventName);
    }

    private AnalyticsEvent(Builder builder) {
        this.dataMap = builder.dataMap;
        this.eventName = builder.eventName;
    }

    public EventName eventName() {
        return eventName;
    }

    @Nullable
    public <T> T getData(AdditionalData data) {
        return dataMap == null ? null : (T) dataMap.get(data);
    }

    public static class Builder {
        private Map<AdditionalData, Object> dataMap;
        private EventName eventName;

        private Builder(EventName eventName) {
            this.eventName = eventName;
        }

        public Builder data(AdditionalData key, Object value) {
            if (dataMap == null) {
                dataMap = new HashMap<>();
            }
            dataMap.put(key, value);
            return this;
        }

        public AnalyticsEvent build() {
            return new AnalyticsEvent(this);
        }
    }
}
