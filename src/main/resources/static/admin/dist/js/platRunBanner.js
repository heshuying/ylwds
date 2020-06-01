new Vue({
    el: '#app',
    data() {
        return {
            tableData: [{
                img: 'http://localhost:8080/upload/20200527/3b5421fdd24348908d58ccc92a371fe0.png',
                url: 'https://lanhuapp.com/web/#/item/project/board?pid=292c8f-5942-43cd-a335-f8b43c95',
                order: '12',
                createDate: '2020/03/03'
            }],
            bannerCount: 25,
            visible: false,
            showBtnWord: '收起'
        };
    },
    mounted() {
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
        }
    }
})