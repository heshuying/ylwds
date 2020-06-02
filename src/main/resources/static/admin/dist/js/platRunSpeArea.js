new Vue({
    el: '#app',
    data() {
        return {
            baseUrl: '',
            tableData: [{
                img: 'http://localhost:8080/upload/20200513/1ad78b7fd874435593fa7b20be689290.png',
                imgOri: '/upload/20200513/1ad78b7fd874435593fa7b20be689290.png',
                url: 'https://lanhuapp.com/web/#/item/project/board?pid=292c8f-5942-43cd-a335-f8b43c95',
                order: '12',
                createDate: '2020/03/03'
            }],
            areaList: [
                {label: '鞋联网', name: '1', intro: '以温度的名义，与时间为敌'},
                {label: '个护清洁', name: '2', intro: '爱干净，爱自己'},
                {label: '宠物用品', name: '3', intro: '漂亮宠物小宝贝'},
            ],
            visible: false,
            showBtnWord: '收起',
            // 专区信息dialog
            speInfoMoralShow: false,
            diaSpeName: '',
            diaSpeTip: '',
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
            activeName: '1'
        };
    },
    mounted() {
        this.baseUrl = window.localStorage.getItem("baseUrl");
        this.visible = true;
    },
    methods: {
        clickUrl(url) {
            console.log(url);
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
            if(this.diaSpeName === '' || this.diaSpeTip === '') {
                this.$message.error('请填写完整信息后重试!');
                return;
            }
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
            console.log(val);
        }
    }
})