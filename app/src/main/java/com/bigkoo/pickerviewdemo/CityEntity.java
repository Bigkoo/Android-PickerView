package com.bigkoo.pickerviewdemo;

import com.bigkoo.pickerview.model.IPickerViewData;
import java.util.List;

/**
 * Created by sunxu on 16/11/21.
 */
public class CityEntity {

    /**
     * region : {"name":"北京市","code":"11","state":[{"name":"北京","code":"01","city":[{"name":"东城区","code":"01"},{"name":"西城区","code":"02"},{"name":"崇文区","code":"03"},{"name":"宣武区","code":"04"},
     * {"name":"朝阳区","code":"05"},{"name":"丰台区","code":"06"},{"name":"石景山区","code":"07"},{"name":"海淀区","code":"08"},{"name":"门头沟区","code":"09"},{"name":"房山区","code":"11"},{"name":"通州区",
     * "code":"12"},{"name":"顺义区","code":"13"},{"name":"昌平区","code":"14"},{"name":"大兴区","code":"15"},{"name":"怀柔区","code":"16"},{"name":"平谷区","code":"17"},{"name":"密云县","code":"28"},{"name":"延庆县",
     * "code":"29"}]}]}
     */

    private List<DataBean> data;


    public List<DataBean> getData() { return data;}


    public void setData(List<DataBean> data) { this.data = data;}


    public static class DataBean {
        /**
         * name : 北京市
         * code : 11
         * state : [{"name":"北京","code":"01","city":[{"name":"东城区","code":"01"},{"name":"西城区","code":"02"},{"name":"崇文区","code":"03"},{"name":"宣武区","code":"04"},{"name":"朝阳区","code":"05"},
         * {"name":"丰台区","code":"06"},{"name":"石景山区","code":"07"},{"name":"海淀区","code":"08"},{"name":"门头沟区","code":"09"},{"name":"房山区","code":"11"},{"name":"通州区","code":"12"},{"name":"顺义区",
         * "code":"13"},{"name":"昌平区","code":"14"},{"name":"大兴区","code":"15"},{"name":"怀柔区","code":"16"},{"name":"平谷区","code":"17"},{"name":"密云县","code":"28"},{"name":"延庆县","code":"29"}]}]
         */

        private RegionBean region;


        public RegionBean getRegion() { return region;}


        public void setRegion(RegionBean region) { this.region = region;}


        public static class RegionBean  implements IPickerViewData{
            private String name;
            private String code;
            /**
             * name : 北京
             * code : 01
             * city : [{"name":"东城区","code":"01"},{"name":"西城区","code":"02"},{"name":"崇文区","code":"03"},{"name":"宣武区","code":"04"},{"name":"朝阳区","code":"05"},{"name":"丰台区","code":"06"},
             * {"name":"石景山区","code":"07"},{"name":"海淀区","code":"08"},{"name":"门头沟区","code":"09"},{"name":"房山区","code":"11"},{"name":"通州区","code":"12"},{"name":"顺义区","code":"13"},{"name":"昌平区",
             * "code":"14"},{"name":"大兴区","code":"15"},{"name":"怀柔区","code":"16"},{"name":"平谷区","code":"17"},{"name":"密云县","code":"28"},{"name":"延庆县","code":"29"}]
             */

            private List<StateBean> state;


            public String getName() { return name;}


            public void setName(String name) { this.name = name;}


            public String getCode() { return code;}


            public void setCode(String code) { this.code = code;}


            public List<StateBean> getState() { return state;}


            public void setState(List<StateBean> state) { this.state = state;}


            @Override public String getPickerViewText() {
                return this.name;
            }


            public static class StateBean implements IPickerViewData{
                private String name;
                private String code;
                /**
                 * name : 东城区
                 * code : 01
                 */

                private List<CityBean> city;


                public String getName() { return name;}


                public void setName(String name) { this.name = name;}


                public String getCode() { return code;}


                public void setCode(String code) { this.code = code;}


                public List<CityBean> getCity() { return city;}


                public void setCity(List<CityBean> city) { this.city = city;}


                @Override public String getPickerViewText() {
                    return this.name;
                }


                public static class CityBean implements IPickerViewData{
                    private String name;
                    private String code;


                    public String getName() { return name;}


                    public void setName(String name) { this.name = name;}


                    public String getCode() { return code;}


                    public void setCode(String code) { this.code = code;}


                    @Override public String getPickerViewText() {
                        return this.name;
                    }
                }
            }
        }
    }
}
