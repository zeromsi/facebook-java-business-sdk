/**
 * Copyright (c) 2015-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to
 * use, copy, modify, and distribute this software in source code or binary
 * form for use in connection with the web services and APIs provided by
 * Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use
 * of this software is subject to the Facebook Developer Principles and
 * Policies [http://developers.facebook.com/policy/]. This copyright notice
 * shall be included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

package com.facebook.ads.sdk;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.facebook.ads.sdk.APIException.MalformedResponseException;

/**
 * This class is auto-genereated.
 *
 * For any issues or feature requests related to this class, please let us know
 * on github and we'll fix in our codegen framework. We'll not be able to accept
 * pull request for this class.
 *
 */
public class ConnectionObjectOpenGraphAction extends APINode {
  @SerializedName("connected_objects")
  private List<String> mConnectedObjects = null;
  @SerializedName("display_name")
  private String mDisplayName = null;
  @SerializedName("name")
  private String mName = null;
  @SerializedName("properties")
  private List<ConnectionObjectOpenGraphObjectProperty> mProperties = null;
  protected static Gson gson = null;

  public ConnectionObjectOpenGraphAction() {
  }

  public String getId() {
    return null;
  }
  public static ConnectionObjectOpenGraphAction loadJSON(String json, APIContext context) {
    ConnectionObjectOpenGraphAction connectionObjectOpenGraphAction = getGson().fromJson(json, ConnectionObjectOpenGraphAction.class);
    if (context.isDebug()) {
      JsonParser parser = new JsonParser();
      JsonElement o1 = parser.parse(json);
      JsonElement o2 = parser.parse(connectionObjectOpenGraphAction.toString());
      if (o1.getAsJsonObject().get("__fb_trace_id__") != null) {
        o2.getAsJsonObject().add("__fb_trace_id__", o1.getAsJsonObject().get("__fb_trace_id__"));
      }
      if (!o1.equals(o2)) {
        context.log("[Warning] When parsing response, object is not consistent with JSON:");
        context.log("[JSON]" + o1);
        context.log("[Object]" + o2);
      };
    }
    connectionObjectOpenGraphAction.context = context;
    connectionObjectOpenGraphAction.rawValue = json;
    return connectionObjectOpenGraphAction;
  }

  public static APINodeList<ConnectionObjectOpenGraphAction> parseResponse(String json, APIContext context, APIRequest request) throws MalformedResponseException {
    APINodeList<ConnectionObjectOpenGraphAction> connectionObjectOpenGraphActions = new APINodeList<ConnectionObjectOpenGraphAction>(request, json);
    JsonArray arr;
    JsonObject obj;
    JsonParser parser = new JsonParser();
    Exception exception = null;
    try{
      JsonElement result = parser.parse(json);
      if (result.isJsonArray()) {
        // First, check if it's a pure JSON Array
        arr = result.getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
          connectionObjectOpenGraphActions.add(loadJSON(arr.get(i).getAsJsonObject().toString(), context));
        };
        return connectionObjectOpenGraphActions;
      } else if (result.isJsonObject()) {
        obj = result.getAsJsonObject();
        if (obj.has("data")) {
          if (obj.has("paging")) {
            JsonObject paging = obj.get("paging").getAsJsonObject().get("cursors").getAsJsonObject();
            String before = paging.has("before") ? paging.get("before").getAsString() : null;
            String after = paging.has("after") ? paging.get("after").getAsString() : null;
            connectionObjectOpenGraphActions.setPaging(before, after);
          }
          if (obj.get("data").isJsonArray()) {
            // Second, check if it's a JSON array with "data"
            arr = obj.get("data").getAsJsonArray();
            for (int i = 0; i < arr.size(); i++) {
              connectionObjectOpenGraphActions.add(loadJSON(arr.get(i).getAsJsonObject().toString(), context));
            };
          } else if (obj.get("data").isJsonObject()) {
            // Third, check if it's a JSON object with "data"
            obj = obj.get("data").getAsJsonObject();
            boolean isRedownload = false;
            for (String s : new String[]{"campaigns", "adsets", "ads"}) {
              if (obj.has(s)) {
                isRedownload = true;
                obj = obj.getAsJsonObject(s);
                for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                  connectionObjectOpenGraphActions.add(loadJSON(entry.getValue().toString(), context));
                }
                break;
              }
            }
            if (!isRedownload) {
              connectionObjectOpenGraphActions.add(loadJSON(obj.toString(), context));
            }
          }
          return connectionObjectOpenGraphActions;
        } else if (obj.has("images")) {
          // Fourth, check if it's a map of image objects
          obj = obj.get("images").getAsJsonObject();
          for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
              connectionObjectOpenGraphActions.add(loadJSON(entry.getValue().toString(), context));
          }
          return connectionObjectOpenGraphActions;
        } else {
          // Fifth, check if it's an array of objects indexed by id
          boolean isIdIndexedArray = true;
          for (Map.Entry entry : obj.entrySet()) {
            String key = (String) entry.getKey();
            if (key.equals("__fb_trace_id__")) {
              continue;
            }
            JsonElement value = (JsonElement) entry.getValue();
            if (
              value != null &&
              value.isJsonObject() &&
              value.getAsJsonObject().has("id") &&
              value.getAsJsonObject().get("id") != null &&
              value.getAsJsonObject().get("id").getAsString().equals(key)
            ) {
              connectionObjectOpenGraphActions.add(loadJSON(value.toString(), context));
            } else {
              isIdIndexedArray = false;
              break;
            }
          }
          if (isIdIndexedArray) {
            return connectionObjectOpenGraphActions;
          }

          // Sixth, check if it's pure JsonObject
          connectionObjectOpenGraphActions.clear();
          connectionObjectOpenGraphActions.add(loadJSON(json, context));
          return connectionObjectOpenGraphActions;
        }
      }
    } catch (Exception e) {
      exception = e;
    }
    throw new MalformedResponseException(
      "Invalid response string: " + json,
      exception
    );
  }

  @Override
  public APIContext getContext() {
    return context;
  }

  @Override
  public void setContext(APIContext context) {
    this.context = context;
  }

  @Override
  public String toString() {
    return getGson().toJson(this);
  }


  public List<String> getFieldConnectedObjects() {
    return mConnectedObjects;
  }

  public ConnectionObjectOpenGraphAction setFieldConnectedObjects(List<String> value) {
    this.mConnectedObjects = value;
    return this;
  }

  public String getFieldDisplayName() {
    return mDisplayName;
  }

  public ConnectionObjectOpenGraphAction setFieldDisplayName(String value) {
    this.mDisplayName = value;
    return this;
  }

  public String getFieldName() {
    return mName;
  }

  public ConnectionObjectOpenGraphAction setFieldName(String value) {
    this.mName = value;
    return this;
  }

  public List<ConnectionObjectOpenGraphObjectProperty> getFieldProperties() {
    return mProperties;
  }

  public ConnectionObjectOpenGraphAction setFieldProperties(List<ConnectionObjectOpenGraphObjectProperty> value) {
    this.mProperties = value;
    return this;
  }

  public ConnectionObjectOpenGraphAction setFieldProperties(String value) {
    Type type = new TypeToken<List<ConnectionObjectOpenGraphObjectProperty>>(){}.getType();
    this.mProperties = ConnectionObjectOpenGraphObjectProperty.getGson().fromJson(value, type);
    return this;
  }



  synchronized /*package*/ static Gson getGson() {
    if (gson != null) {
      return gson;
    } else {
      gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.STATIC)
        .excludeFieldsWithModifiers(Modifier.PROTECTED)
        .disableHtmlEscaping()
        .create();
    }
    return gson;
  }

  public ConnectionObjectOpenGraphAction copyFrom(ConnectionObjectOpenGraphAction instance) {
    this.mConnectedObjects = instance.mConnectedObjects;
    this.mDisplayName = instance.mDisplayName;
    this.mName = instance.mName;
    this.mProperties = instance.mProperties;
    this.context = instance.context;
    this.rawValue = instance.rawValue;
    return this;
  }

  public static APIRequest.ResponseParser<ConnectionObjectOpenGraphAction> getParser() {
    return new APIRequest.ResponseParser<ConnectionObjectOpenGraphAction>() {
      public APINodeList<ConnectionObjectOpenGraphAction> parseResponse(String response, APIContext context, APIRequest<ConnectionObjectOpenGraphAction> request) throws MalformedResponseException {
        return ConnectionObjectOpenGraphAction.parseResponse(response, context, request);
      }
    };
  }
}
