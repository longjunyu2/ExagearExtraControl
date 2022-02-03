/*
 * Copyright 2022 Junyu Long
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.junyulong.ecc.core.model.layout.layer.view;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EecInputViewModel {
    private String id;                  // 控件唯一Id

    private String type;                // 控件类型

    private Bind bind;                  // 控件位置约束

    private Map<String, String> info;   // 控件信息

    private Param param;                // 控件宽高参数

    public EecInputViewModel() {

    }

    public EecInputViewModel(String type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.info = new HashMap<>();
        this.bind = new Bind();
    }

    public EecInputViewModel(String type, String id) {
        this.id = id;
        this.type = type;
        this.info = new HashMap<>();
    }

    public EecInputViewModel(String type, String id, Map<String, String> map, Bind bind) {
        this.id = id;
        this.type = type;
        this.info = new HashMap<>(map);
        this.bind = bind;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

    public Bind getBind() {
        return bind;
    }

    public void setBind(Bind bind) {
        this.bind = bind;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public static class Param {
        public int width;

        public int height;

        public Param(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static class Bind {

        public BindInfo horizontal;     // 水平绑定信息

        public BindInfo vertical;       // 垂直绑定信息

        public Bind() {

        }

        public Bind(BindInfo horizontal, BindInfo vertical) {
            this.horizontal = horizontal;
            this.vertical = vertical;
        }

        public static class BindInfo {

            public BindInfo(String id, String mode, int offset) {
                this.referenceId = id;
                this.referenceMode = mode;
                this.offset = offset;
            }

            public String referenceId;      // 参考对象Id

            public String referenceMode;    // 参考模式

            public int offset;               // 偏移量
        }

    }


}
