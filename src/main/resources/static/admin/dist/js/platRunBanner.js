new Vue({
    el: '#app',
    data() {
        return {
            baseUrl: '',
            tableData: [],
            visible: false,
            showBtnWord: '展示位置',
            moralTitle: '新增轮播',
            moralShow: false,
            closeBtnShow: false,
            postInfo: {}
        };
    },
    mounted() {
        this.baseUrl = window.localStorage.getItem("baseUrl");
        this.getBannerList();
    },
    methods: {
        // 获取轮播列表
        getBannerList() {
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                url: '/admin/module/getBannerDetails',
                type: 'GET',
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.tableData = [];
                        for(var i in result.data) {
                            result.data[i].imgUrlShow = _this.baseUrl + result.data[i].imgUrl;
                        }
                        _this.tableData = result.data || [];
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
        // 跳转到专区
        goSpeArea() {
            window.location.href = "/admin/module/platRunSpeArea";
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
                this.postInfo.imgUrl = res.data;
                this.postInfo.imgUrlShow = this.baseUrl + res.data;
            } else {
                this.$message.error("上传失败，" + res.msg);
            }
        },
        // 新增banner
        addBanner() {
            this.moralTitle = '新增轮播';
            this.moralShow = true;
            this.postInfo = {
                imgUrl: '',
                imgUrlShow: '',
                jumpUrl: '',
                modRank: ''
            }
        },
        // 修改banner
        editBanner(item) {
            this.moralTitle = '修改轮播';
            this.moralShow = true;
            this.postInfo = {
                id: item.id,
                imgUrl: item.imgUrl,
                imgUrlShow: item.imgUrlShow,
                jumpUrl: item.jumpUrl,
                modRank: item.modRank
            }
        },
        // 保存banner
        saveBanner() {
            if(this.postInfo.imgUrl === '' || this.postInfo.jumpUrl === '' || this.postInfo.modRank === '') {
                this.$message.error('请填写完整信息后重试!');
                return;
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
                url: '/admin/module/saveBannerDetail',
                contentType: "application/json",
                data: JSON.stringify(this.postInfo),
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.$message.success('操作成功');
                        _this.getBannerList();
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
        }
    }
})