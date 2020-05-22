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
                orderDate: [],
                orderId: '',
                tableData: [],
                sortType: '',
                expressComArr: [
                    {label:'顺丰',value: 'shunfeng'},
                    {label:'中通',value: 'zhongtong'},
                    {label:'申通',value: 'shentong'},
                    {label:'圆通',value: 'yuantong'},
                    {label:'韵达',value: 'yunda'},
                    {label:'京东物流',value: 'jd'},
                    {label:'EMS',value: 'ems'},
                ],
                expressCompany: '',
                expressNumber: '',
                sendOrderId: '',
                moralShow: false, // 填写快递公司和单号moral
                closeBtnShow: false,
                total: 0,
                pagesize: 10,
                currentPage: 1,
                // 退款申请dialog
                refundOrderId: '',
                applyMoralShow: false,
                refundApplyGoodsName: '',
                refundApplyBuyNum: '',
                refundApplyRetNum: '',
                refundApplyReason: '',
                refundApplyDetail: '',
                providerOpeateRadio: '', // 卖家退货操作
                rejectReason: '', // 卖家拒绝退货原因
                // 地址管理dialog
                addressMoralShow: false,
                addressRemark: '',  // 地址备注
                addressStep: '2',
                refundSelectedAddress: '',
                currentAddress: {},
                refundAddressList: [],
                refundAddressProvince: '',
                refundAddressCity: '',
                refundAddressDis: '',
                refundAddressAcceptor: '',
                refundAddressPhone: '',
                refundAddressDetail: '',
                // 等待买家退回dialog
                waitBuyerReturnMoralShow: false,
                // 包裹寄回中查看物流dialog
                checkExpressMoralShow: false,
                expressStatums: [],
                // 编辑退款dialog
                editReturnMoneyMoralShow: false,
                returnMoneyTypeRadio: "",
                isAddressDiaShow: false
            };
        },
         created() {
             this.addressMoralShow = true;
         },
        mounted() {
            this.addressMoralShow = false;
            this.queryTableData();
        },
        methods: {
            // 复制地址
            copyAddress(address) {
                var input = document.createElement('input');
                document.body.appendChild(input);
                input.setAttribute('value', address);
                input.select();
                if (document.execCommand('copy')) {
                    document.execCommand('copy');
                    this.$message.success('地址已复制');
                }
                document.body.removeChild(input);
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
            // 导出excel
            exportSheet() {
                var beginTime =  '';
                var endTime =  '';
                if(this.orderDate && this.orderDate.length > 0) {
                    beginTime = this.orderDate[0];
                    endTime = this.orderDate[1];
                }
                window.location.href = '/admin/orders/export?id=' + this.orderId + '&status=' + this.orderStatus + '&beginTime=' + beginTime + '&endTime=' + endTime;
            },
            // 导入excel
            handleUploadSuccess(res) {
                if (res.code == "200") {
                    this.$message.success("上传成功！");
                    this.queryTableData();
                } else {
                    this.$message.error('上传失败，'+ res.msg);
                }
            },
            // 点击发货
            singleSendGoods(valObj) {
                this.moralShow = true;
                this.sendOrderId = valObj.id;
            },
            // 单个发货服务
            singleSendGoodsService() {
                var _this = this;
                if(this.expressCompany === ''|| this.expressNumber === '') {
                    this.$message.error('物流公司及物流单号不能为空');
                    return;
                }
                var expressName = '';
                for(var i in this.expressComArr) {
                    if(this.expressComArr[i].value === this.expressCompany) {
                        expressName = this.expressComArr[i].label;
                    }
                }
                var postObj = {
//                    userId:[[${session.loginUserId}]],
                    orderId: this.sendOrderId,
                    expressCode: this.expressCompany,
                    expressCompany: expressName,
                    expressNumber: this.expressNumber
                }
                $.ajax({
                    type: "post",
                    url: "/admin/orders/deliverGoods",
                    contentType: "application/json",
                    data: JSON.stringify(postObj),
                    success: function (result) {
                        if (result.code == 200) {
                            _this.$message.success('发货成功');
                            _this.moralShow = false;
                            _this.queryTableData();
                        } else {
                            _this.$message.error(result.message);
                        }
                    }
                });
            },
            // 换页
            current_change: function (currentPage) {
                this.currentPage = currentPage;
                this.queryTableData();
            },
            // 查询表格数据
            queryTableData() {
                var beginTime =  '';
                var endTime =  '';
                if(this.orderDate && this.orderDate.length > 0) {
                    beginTime = this.orderDate[0];
                    endTime = this.orderDate[1];
                }
                var _this = this;
                var loading = this.$loading({
                    lock: true,
                    text: "拼命加载中, 请稍等...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                $.ajax({
                    url: '/admin/orders/supplier?page=' + this.currentPage + '&limit=' + this.pagesize +
                    '&id=' + this.orderId + '&status=' + this.orderStatus + '&beginTime=' + beginTime +
                    '&endTime=' + endTime + '&descOrAsc=' + this.sortType,
                    type: 'GET',
                    success: function (result) {
                        loading.close();
                        if (result.resultCode == 200) {
                            _this.total = result.data.totalCount || 0;
                            for (var i in result.data.list) {
                                result.data.list[i].totalPriceShow = '¥' + result.data.list[i].totalPrice;
                            }
                            _this.tableData = result.data.list || [];
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
            },
            // 点击修改地址
            // 修改或新增地址
            addressChange(type, item) {
                this.currentAddress = {};
                // 新增地址
                if (type === "1") {
                    this.currentAddress = {
                        acceptor: "",
                        area: "",
                        city: "",
                        detail: "",
                        isDefault: false,
                        phone: "",
                        province: "",
                        street: ""
                    };
                    $("#province").val('');
                    $("#province").trigger("change");
                    $("#city").val('');
                    $("#city").trigger("change");
                    $("#district").val('');
                    $("#district").trigger("change");
                } else if (type === "2") {
                    // 更改地址
                    this.currentAddress = item;
                    $("#province").val(item.province);
                    $("#province").trigger("change");
                    $("#city").val(item.city);
                    $("#city").trigger("change");
                    $("#district").val(item.area);
                    $("#district").trigger("change");
                }
                this.addressStep = "2";
            },
            hangdleProviceChange(val) {
                this.currentAddress.province = val.target.value;
                this.currentAddress.city = '';
                this.currentAddress.area = '';
            },
            hangdleCityChange(val) {
                this.currentAddress.city = val.target.value;
                this.currentAddress.area = '';
            },
            hangdleDisChange(val) {
                this.currentAddress.area = val.target.value;
            },
            // 保存地址
            saveAddress() {
                var _this = this;
                var url = "/admin/delivery/add";
                if (this.currentAddress.addrId) {
                    url = "/admin/delivery/update";
                }
                if(this.currentAddress.area === '' || this.currentAddress.acceptor === '' || this.currentAddress.phone === '' || this.currentAddress.detail === '') {
                    _this.$message.error('请填写完整地址信息');
                    return;
                }
                $.ajax({
                    type: "post",
                    url: url,
                    contentType: "application/json",
                    data: JSON.stringify(this.currentAddress),
                    success: function (result) {
                        if (result.resultCode == 200) {
                            _this.$message.success('操作成功');
                            _this.addressStep = "1";
                            _this.getAddressList();
                        } else {
                            _this.$message.error(result.message);
                        }
                    }
                });
            },
            // 退货申请审核
            returnCheck(item) {
                var _this = this;
                var loading = this.$loading({
                    lock: true,
                    text: "拼命加载中, 请稍等...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                this.refundOrderId = item.id;
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
                            _this.applyMoralShow = true;
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
            },
            // 查看物流信息
            returnExpress(item) {
                this.checkExpressMoralShow = true;
                var _this = this;
                var loading = this.$loading({
                    lock: true,
                    text: "拼命加载中, 请稍等...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                $.ajax({
                    url: '/api/kd100/query?com=jd&num=JD0016164893471',
                    type: 'GET',
                    success: function (result) {
                        loading.close();
                        if (result.resultCode == 200) {
                            _this.expressStatums = result.data.data || [];
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
            },
            // 退款驳回
            refundReject() {
                var _this = this;
                if(this.rejectReason === '') {
                    this.$message.error('请先输入驳回原因');
                    return;
                }
                var postObj = {
                    orderId: this.refundOrderId,
                    status: '12',
                    rejectReason: this.rejectReason
                }
                $.ajax({
                    type: "post",
                    url: "/admin/refund/check",
                    contentType: "application/json",
                    data: JSON.stringify(postObj),
                    success: function (result) {
                        if (result.resultCode == 200) {
                            _this.$message.success('操作成功');
                            _this.applyMoralShow = false;
                            _this.queryTableData();
                        } else {
                            _this.$message.error(result.message);
                        }
                    }
                });
            },
            // 退货申请同意
            refundAgree() {
                this.applyMoralShow = false;
                this.addressMoralShow = true;
                this.isAddressDiaShow = true;
                this.addressStep = '1';
                this.getAddressList();
            },
            // 获取地址列表
            getAddressList() {
                var _this = this;
                var loading = this.$loading({
                    lock: true,
                    text: "拼命加载中, 请稍等...",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                $.ajax({
                    url: '/admin/delivery',
                    type: 'GET',
                    success: function (result) {
                        loading.close();
                        if (result.resultCode == 200) {
                            for (var i in result.data) {
                                result.data[i].addressAll =
                                    result.data[i].province +
                                    result.data[i].city +
                                    result.data[i].area +
                                    result.data[i].detail;
                                if (result.data[i].isDefault) {
                                    _this.refundSelectedAddress = result.data[i].addrId;
                                }
                            }
                            _this.refundAddressList = result.data || [];
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
    })