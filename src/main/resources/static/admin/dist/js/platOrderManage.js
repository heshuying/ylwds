new Vue({
    el: '#app',
    data() {
        return {
            orderStatus: '',
            orderStatusArr: [
                {label:'请选择',value: ''},
                {label:'待支付',value: '1'},
                {label:'待发货',value: '2'},
                {label:'待收货',value: '3'},
                {label:'待评价',value: '4'},
                // {label:'售后中',value: '5'},
                {label:'待结算',value: '5'},
                {label:'结算中',value: '7'},
                {label:'已结算',value: '8'},
                {label:'已取消',value: '10'},
            ],
            orderId: '',
            tableData: [],
            multipleSelection: [],
            canCutList: [], //记录上传照片的订单id
            sortType: '',
            total: 0,
            pagesize: 10,
            currentPage: 1,
            closeBtnShow: false,
            // 退货申请/退货审核dialog
            applyMoralShow: false,
            isApply: false,
            refundApplyGoodsName: '',
            refundApplyBuyNum: '',
            refundApplyRetNum: '',
            refundApplyReason: '',
            refundApplyDetail: '',
            refundApplyRejectReason: '',
            // 包裹寄回中dialog
            checkExpressMoralShow:false,
            activities: [{
                content: '活动按期开始',
                timestamp: '2018-04-15'
            }, {
                content: '通过审核',
                timestamp: '2018-04-13'
            }, {
                content: '创建成功',
                timestamp: '2018-04-11'
            }],
            // 待买家确认退款金额/买家申诉/退款明细dialog
            mutiMoralShow: false,
            mutiTitle: '待买家确认退款金额',
        };
    },
    mounted() {
        this.queryTableData();
    },
    methods: {
        // 表格数据勾选修改
        handleTableSelectionChange(val) {
            this.multipleSelection = val;
        },
        // 表格上排序方式发生改变
        sortChange(column) {
            if (column.order.indexOf('desc') > -1) {
                this.sortType = 'desc';
            } else {
                this.sortType = 'asc';
            }
            this.queryTableData();
        },
        // 换页
        current_change: function (currentPage) {
            this.currentPage = currentPage;
            this.queryTableData();
        },
        // 查询表格数据
        queryTableData() {
            var _this = this;
            var loading = this.$loading({
                lock: true,
                text: "拼命加载中, 请稍等...",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            $.ajax({
                url: '/admin/orders/platform?page=' + this.currentPage + '&limit=' + this.pagesize +
                '&id=' + this.orderId + '&status=' + this.orderStatus + '&descOrAsc=' + this.sortType,
                type: 'GET',
                success: function (result) {
                    loading.close();
                    if (result.resultCode == 200) {
                        _this.total = result.data.totalCount || 0;
                        for (var i in result.data.list) {
                            var totalTemp = result.data.list[i].totalPrice === null ?  '' : result.data.list[i].totalPrice;
                            result.data.list[i].totalPriceShow = '¥' + totalTemp;
                            var formatGross = result.data.list[i].grossProfit === null ?  '' : result.data.list[i].grossProfit;
                            result.data.list[i].grossProfitShow = '¥' + formatGross;
                            result.data.list[i].cutDownDis = result.data.list[i].status == '待支付'? false : true;
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
        // 减免前上传图片
        handleUploadSuccess(res,file,fileList,item) {
            if (res.resultCode == "200") {
                this.$message.success('上传成功');
                var wholeUrl = this.baseUrl + res.data;
                this.canCutList.push({orderId: item.id, img: res.data});
            } else {
                this.$message.error("上传失败，" + res.msg);
            }
        },
        // 平台减免服务
        cutDownService(valObj) {
            var imgFlag = false;
            var cutImg = '';
            var operateIndex = -1;
            for(var i in this.canCutList) {
                if(this.canCutList[i].orderId === valObj.id) {
                    operateIndex = i;
                    imgFlag = true;
                    cutImg = this.canCutList[i].img;
                    break;
                }
            }
            if(!imgFlag) {
                this.$message.error('请先上传签名图片');
                return;
            }
            if(valObj.cutDown === '' || valObj.cutDown == '0') {
                this.$message.error('前先输入减免金额');
                return;
            }
            var _this = this;
            var postObj = {
//                    userId:[[${session.loginUserId}]],
                orderId: valObj.id,
                cutdownNumber: parseFloat(valObj.cutDown),
                cutdownImg: cutImg
            }
            $.ajax({
                type: "post",
                url: "/admin/orders/cutDown",
                contentType: "application/json",
                data: JSON.stringify(postObj),
                success: function (result) {
                    if (result.resultCode == 200) {
                        _this.canCutList.splice(operateIndex, 1);
                        _this.$message.success('操作成功');
                        _this.queryTableData();
                    } else {
                        _this.$message.error(result.message);
                    }
                }
            });
        },
        // 订单操作
        operateOrder(item) {
            if(item.status === '退货中待商家确认' || item.status === '退货中商家驳回') {
                this.applyMoralShow = true;
                var _this = this;
                var loading = this.$loading({
                    lock: true,
                    text: "拼命加载中, 请稍等...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                $.ajax({
                    url: '/admin/refund/info?orderId=' + item.id,
                    type: 'GET',
                    success: function (result) {
                        loading.close();
                        if (result.resultCode == 200) {
                            _this.refundApplyGoodsName = result.data.goodsName || '';
                            _this.refundApplyBuyNum = result.data.buyNum || '';
                            _this.refundApplyRetNum = result.data.refundNum || '';
                            _this.refundApplyReason = result.data.refundReasonDesc || '';
                            _this.refundApplyDetail = result.data.refundDetail || '';
                            _this.refundApplyRejectReason = result.data.rejectReason || '';
                            _this.applyMoralShow = true;
                            _this.isApply= true;
                            if(item.status === '退货中商家驳回') {
                                _this.isApply= false;
                            }
                        } else {
                            _this.$message.error(result.message);
                        }
                        ;
                    },
                    error: function () {
                        loading.close();
                        _this.$message.error('操作失败');
                    }
                });
            }
        }
    }
})