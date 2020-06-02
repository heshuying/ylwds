new Vue({
    el: '#app',
    data() {
        return {
            baseUrl: '',
            tableData: [{
                img: 'http://localhost:8080/upload/20200527/3b5421fdd24348908d58ccc92a371fe0.png',
                imgOri: '/upload/20200527/3b5421fdd24348908d58ccc92a371fe0.png',
                url: 'https://lanhuapp.com/web/#/item/project/board?pid=292c8f-5942-43cd-a335-f8b43c95',
                order: '12',
                createDate: '2020/03/03'
            }],
            visible: false,
            showBtnWord: '收起',
            moralTitle: '新增轮播',
            moralShow: false,
            closeBtnShow: false,
            wholeUrl: '',
            jumpUrl: '',
            sortNum: ''
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
        // 新增banner
        addBanner() {
            this.moralTitle = '新增轮播';
            this.moralShow = true;
            this.wholeUrl = '';
            this.jumpUrl = '';
            this.sortNum = '';
        },
        // 修改banner
        editBanner(item) {
            this.moralTitle = '修改轮播';
            this.moralShow = true;
            this.wholeUrl = this.baseUrl + item.imgOri;
            this.jumpUrl = item.url;
            this.sortNum = item.order;
        },
        // 保存banner
        saveBanner() {
            if(this.wholeUrl === '' || this.jumpUrl === '' || this.sortNum === '') {
                this.$message.error('请填写完整信息后重试!');
                return;
            }
        }
    }
})