new Vue({
    el: '#app',
    data() {
        return {
            baseUrl: '',
            tableData: [],
            areaList: [], // 专区列表
            currAreaInfo: {}, // 当前专区
            visible: false,
            showBtnWord: '展示位置',
            // 专区信息dialog
            speInfoMoralShow: false,
            speInfoName: '',
            speInfoDesc: '',
            // 专区产品dialog
            moralTitle: '新增轮播',
            moralShow: false,
            closeBtnShow: false,
            wholeUrl: '',
            jumpUrl: '',
            speGoodsName: '',
            sortNum: '',
            total: 0,
            pagesize: 10,
            currentPage: 1,
            activeName: '',
            showPosition: 30 // 展示位置
        };
    },
    mounted() {
        this.baseUrl = window.localStorage.getItem("baseUrl");
        this.getSpeList();
    },
    methods: {
        // 获取专区列表--鞋联网等
        getSpeList() {
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                url: '/admin/module/getModules',
                type: 'GET',
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.areaList = result.data || [];
                        if(result.data && result.data.length > 0) {
                            _this.currAreaInfo =  result.data[0];
                            _this.activeName = result.data[0].id + '';
                            _this.speInfoName = result.data[0].modName + '';
                            _this.speInfoDesc = result.data[0].modDesc + '';
                            _this.getSpeGoods(result.data[0].id);
                        }
                    } else {
                        _this.$message.error(result.message);
                    };
                },
                error: function () {
                    loading.close();
                    _this.$message.error('操作失败');
                }
            });
        },
        // 获取某个专区中的商品
        getSpeGoods(id) {
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                url: '/admin/module/getModuleDetails?modId='+ id + '&limit=' + this.pagesize + '&page='+ this.currentPage,
                type: 'GET',
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.total = result.data.totalCount || 0;
                        for(var i in result.data.list) {
                            result.data.list[i].imgUrlShow = _this.baseUrl + result.data.list[i].imgUrl;
                        }
                        _this.tableData = result.data.list || [];
                    } else {
                        _this.$message.error(result.message);
                    };
                },
                error: function () {
                    loading.close();
                    _this.$message.error('操作失败');
                }
            });
        },
        clickUrl(url) {
            window.open(url,'_blank');
        },
        // 跳转到轮播图页面
        goBannerArea() {
            window.location.href = "/admin/module/platRunBanner";
        },
        showOrHideArea() {
            this.visible = !this.visible;
            if(this.visible) {
                this.showBtnWord = '收起';
            }else {
                this.showBtnWord = '展示位置';
            }
        },
        // 上传成功
        handleUploadSuccess(res) {
            if (res.resultCode == "200") {
                this.$message.success('上传成功');
                this.wholeUrl = this.baseUrl + res.data;
            } else {
                this.$message.error("上传失败，" + res.msg);
            }
        },
        // 编辑专区信息
        editSpeArea() {
            this.speInfoMoralShow = true;
        },
        // 保存专区信息
        saveSpeInfo() {
            if(this.speInfoName === '' || this.speInfoDesc === '') {
                this.$message.error('请填写完整信息后重试!');
                return;
            }
            var postObj = {
                id: this.activeName,
                modName: this.speInfoName,
                modDesc: this.speInfoDesc
            }
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                type: 'post',
                url: '/admin/module/save',
                contentType: "application/json",
                data: JSON.stringify(postObj),
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.$message.success('操作成功');
                        _this.getSpeList();
                        _this.speInfoMoralShow = false;
                    } else {
                        _this.$message.error(result.message);
                    };
                },
                error: function () {
                    loading.close();
                    _this.$message.error('操作失败');
                }
            });
        },
        // 新增专区产品
        addSpeGoods() {
            this.moralTitle = '新增';
            this.moralShow = true;
            this.wholeUrl = '';
            this.jumpUrl = '';
            this.sortNum = '';
        },
        // 修改专区产品
        editSpeGoods(item) {
            this.moralTitle = '编辑';
            this.moralShow = true;
            this.wholeUrl = this.baseUrl + item.imgOri;
            this.jumpUrl = item.url;
            this.sortNum = item.order;
        },
        // 换页
        current_change() {

        },
        // 切换tab
        handleTabClick(val) {
            this.visible = false;
            this.showBtnWord = '展示位置';
            this.showPosition = parseInt(val.index) * 30 + 30;
            this.currAreaInfo = this.areaList[val.index];
            this.speInfoName = this.currAreaInfo.modName;
            this.speInfoDesc = this.currAreaInfo.modDesc;
            this.getSpeGoods(this.currAreaInfo.id);
        }
    }
})