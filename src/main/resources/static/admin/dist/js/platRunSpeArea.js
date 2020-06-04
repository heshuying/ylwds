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
            jumpUrl: '',
            speGoodsName: '',
            sortNum: '',
            total: 0,
            pagesize: 10,
            currentPage: 1,
            activeName: '',
            showPosition: 30, // 展示位置
            currSpeGoods: {}, // 当前选中商品
            goodsList: [], // 添加--编辑专区商品中的商品列表
            state2: '',
        };
    },
    mounted() {
        this.baseUrl = window.localStorage.getItem("baseUrl");
        this.getSpeList();
        this.getGoodsList();
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
        querySearch(queryString, cb) {
            var restaurants = this.goodsList;
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return function(restaurant) {
                return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },
        // 获取商品列表
        getGoodsList() {
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                url: "/admin/goods/listsimple?goodsName=",
                type: 'GET',
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        for(var i in result.data) {
                            result.data[i].value = result.data[i].goodsName;
                        }
                        _this.goodsList = result.data || [];
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
                this.currSpeGoods.imgUrl = res.data;
                this.currSpeGoods.imgUrlShow = this.baseUrl + res.data;
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
            this.currSpeGoods = {
                goodsId: "",
                imgUrl: "",
                imgUrlShow: "",
                isHead: "0",
                jumpUrl: "",
                modId: this.currAreaInfo.id,
                modRank: ""
            }
            this.moralTitle = '新增';
            this.moralShow = true;
        },
        // 修改专区产品
        editSpeGoods(item) {
            this.currSpeGoods = {
                goodsName: item.goodsName,
                goodsId: item.goodsId,
                id: item.id,
                imgUrl: item.imgUrl,
                imgUrlShow: item.imgUrlShow,
                isHead: item.isHead,
                jumpUrl: item.jumpUrl,
                modId: item.modId,
                modRank: item.modRank
            }
            this.moralTitle = '编辑';
            this.moralShow = true;
        },
        // 保存专区产品
        saveSpeGoods() {
            if(this.currSpeGoods.isHead === '0'){
                if(this.currSpeGoods.imgUrl === '' || this.currSpeGoods.goodsId === '' || this.currSpeGoods.modRank === ''){
                    this.$message.error('请维护完整信息后重试');
                    return;
                }
            } else if(this.currSpeGoods.isHead === '1') {
                if(this.currSpeGoods.imgUrl === '' || this.currSpeGoods.jumpUrl === ''){
                    this.$message.error('请维护完整信息后重试');
                    return;
                }
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
                url: '/admin/module/saveModelDetail',
                contentType: "application/json",
                data: JSON.stringify(this.currSpeGoods),
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.$message.success('操作成功');
                        _this.getSpeGoods(_this.currAreaInfo.id);
                        _this.moralShow = false;
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
        // 换页
        current_change(currentPage) {
            this.currentPage = currentPage;
            this.getSpeGoods(this.currAreaInfo.id);
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
        },
        // 商品名称--带提示输入框值发生改变
        handleGoodsSelect(val) {
            this.currSpeGoods.goodsId = val.goodsId;
        }
    }
})