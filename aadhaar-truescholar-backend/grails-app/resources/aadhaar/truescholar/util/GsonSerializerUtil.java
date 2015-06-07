package aadhaar.truescholar.util;
/*
 * Copyright (c) 2015 Khosla Labs Pvt. Ltd. All rights reserved.
 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code distribution. You may obtain
 * a copy of the License at
 *
 *         http://www.khoslalabs.com/licenses
 *
 * Unless required by applicable law or agreed to in writing, this file is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the specific language
 * governing permissions and limitations under the License.
 */

import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonSerializerUtil {
    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decodeBase64(json.getAsString());
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeBase64String(src));

        }
    }

    public static final Gson customGson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
            new ByteArrayToBase64TypeAdapter()).create();


    public static <T extends Object> T unmarshal(String json, Class<T> type) {
        return customGson.fromJson(json, type);
    }

    public static <T extends Object> String marshall(T t) {
        return customGson.toJson(t);
    }
}
