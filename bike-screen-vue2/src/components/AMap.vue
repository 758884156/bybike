<template>
    <div class="content">
        <div class="search-box">
            <el-input v-model="mapAddress.address" disabled placeholder="请选择地址"></el-input>
            <el-button type="primary" @click="doSave"><i class="el-icon-lollipop"></i></el-button>
        </div>
        <div id="map-container" style="min-height: 500px;min-width: 700px;"></div>
    </div>
</template>
<script>
import AMapLoader from "@amap/amap-jsapi-loader"
export default {
    /* eslint-disable */
    name: "Amap",
    props: {
        address: Object,
        mapVisiable: Boolean
    },
    data() {
        return {
            form: {
                address: "",
                province: "",
                city: "",
                lon: "",
                lat: ""
            },
            mapAddress: {},
            map: null,
            lnglat: [114.268691, 30.401227],
            auto: null,
            placeSearch: null,
            markers: []
        }
    },
    mounted() {
        window._AMapSecurityConfig = {
            securityJsCode: '9003a3e15b8dfe3ddf62f64e7ce38b86' // '「申请的安全密钥」',
        }
        this.mapAddress = this.address
        this.initMap()
    }, watch: {
        mapVisiable(newVal) {
            if (newVal == false) {
                this.$emit('getMapData', this.form)
            }
        },
        address(newVal) {
            this.mapAddress = newVal
        }
    }, methods: {
        doSave() {
            this.$emit('closeMap', this.form)
        },
        initMap() {
            if (this.mapAddress.lat != "" && this.mapAddress.lon != "")
                this.lnglat = [this.mapAddress.lon, this.mapAddress.lat]
            AMapLoader.load({
                key: '25f9457e91151fa8473d5d9c87e46240',
                plugins: ["AMap.AutoComplete", "AMap.PlaceSearch", 'AMap.Geolocation']
            }).then((AMap) => {
                this.map = new AMap.Map("map-container", {
                    viewMode: "2D",
                    zoom: 16,
                    center: this.lnglat,
                    resizeEnable: true
                })
                  this.setMarker(this.lnglat)
                this.map.on("click", this.clickMapHandler)
            }).catch(e => {
                console.log(e)
            })
        },
        clickMapHandler(e) {
            this.form.lat = e.lnglat.getLat();
            this.form.lon = e.lnglat.getLng();
            this.lnglat = [e.lnglat.getLng(), e.lnglat.getLat()];
            this.setMarker(this.lnglat)
            let that = this;
            var geocoder;
            this.map.plugin(["AMap.Geocoder"], function () {
                geocoder = new AMap.Geocoder({
                    city: "全国"
                })
                geocoder.getAddress(e.lnglat, function (status, result) {
                    if (status === "complete" && result.info === "OK") {
                        that.mapAddress.address = result.regeocode.formattedAddress
                        that.form.address = result.regeocode.formattedAddress
                        that.form.city = result.regeocode.addressComponent.city
                        that.form.province = result.regeocode.addressComponent.province
                    }
                })
            })
        }, setMarker(lnglat) {
            this.removeMarker()
            let marker = new AMap.Marker({
                position: lnglat
            })
            marker.setMap(this.map);
            this.markers.push(marker)
        },
        removeMarker() {
            if (this.markers && this.markers.length > 0) {
                this.map.remove(this.markers);
                this.markers = []
            }
        }
    }
}
</script>

<style scoped>
.search-box {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    height: 50px;
}

.search-box .label {
    width: 400px;
}

.content {
    position: relative;
}

#map-container {
    overflow: hidden;
    width: 100%;
    height: 500px;
    margin: 0;
}
</style>