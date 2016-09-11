<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>
        请选择支付方式
        </title>
        <link rel="stylesheet" type="text/css" href="css/main.css">
        
    </head>
    
    <body>
        <div class="p-header">
            <div class="w">
               <!--  <div id="logo">
                    <img src="css/logo.png" alt="京东 收银台" height="28" width="170">
                </div> -->
            </div>
        </div>

        <div class="main">
            <div class="w">
                <!-- order 订单信息 -->
                <div class="order">
                    <div class="o-left">
                        <h3 class="o-title">
                            订单提交成功，请您尽快付款！ 订单号：21197570348
                        </h3>
                        <p class="o-tips">
                            请您在提交订单后
                            <span class="font-red">
                                24小时
                            </span>
                            内完成支付，否则订单会自动取消。
                        </p>
                    </div>
                    <div class="o-right">
                        <div class="o-price">
                            <em>
                                应付金额
                            </em>
                            <strong>
                                134.00
                            </strong>
                            <em>
                                元
                            </em>
                        </div>
                       
                    </div>
                    <div class="clr">
                    </div>
                    <div class="o-qrcode">
                    </div>
                    <div style="display: block;" class="o-list j_orderList" id="listPayOrderInfo">
                        <!-- 单笔订单 -->
                        <div class="o-list-info">
                            <span class="mr10" id="shdz">
                                收货地址：广东广州市广州大学城广东工业大学 生活西区 三栋 440
                            </span>
                            <span class="mr10" id="shr">
                                收货人：陈永华
                            </span>
                            <span id="mobile">
                                188****0346
                            </span>
                        </div>
                        <!-- 商品列表 -->
                        <div class="o-list-info">
                            <span>
                                商品名称：欢乐果园 墨西哥进口牛油果 6个 单果约140-160g 自营水果，树懒果园 墨西哥牛油果 6个 1-1.2kg 进口水果 新鲜鳄梨
                            </span>
                        </div>
                        <!-- 单笔订单 end -->
                    </div>
                </div>

                
                <div class="payment">
                    <div class="pay-verify" id="pay-verify-typeCredit">
                        <div class="pv-line" >
                            <div class="count" ><span>应付金额：</span><b>134.00</b> 元</div>
                            <div class="count" > 钱包余额：<b>10</b> 元</div>
                            
                             <span class="pv-msg"  style="display:none">
                                <strong class="font-red">
                                    您还差
                                    <span id="remainShouldPayAmountSpan">
                                        134.00
                                    </span>
                                     元，请<a href="#">前往充值。</a>
                                </strong>
                            </span>
                            <script type="text/javascript">
                                // 检测钱包是否够钱
                                chcekCount ();
                                function chcekCount () {
                                   var Msg = document.getElementsByClassName("pv-msg")[0];
                                   var a1 = Number((document.getElementsByClassName("count")[0].getElementsByTagName("b")[0]).innerHTML);
                                   var a2 = Number((document.getElementsByClassName("count")[1].getElementsByTagName("b")[0]).innerHTML);
                                   if (a1<a2) {
                                       Msg.style.display= "none";
                                   }else{
                                        var remainShouldPayAmountSpan = document.getElementById("remainShouldPayAmountSpan");
                                        remainShouldPayAmountSpan.innerHTML = (a1 - a2).toFixed(2);
                                       Msg.style.display= "block";
                                   }                                
                               }
                               
                            </script>
                        </div>
                            
                        <br>

                        <div class="pv-line" id="pv-line-password" style="display:block">
                            <input placeholder="请输入密码支付" id="payPwd" name="payPwd" 
                            class="ui-input pv-input-password" autocomplete="new-password" type="password">
                            <a href="#"class="ml10" target="_blank" >
                                忘记支付密码？
                            </a>
                        </div>
                        <div class="pv-msg" id="pv-line-haveValidated" style="display:none">
                            <span class="pv-msg-success">
                                您的支付密码已经校验通过，请放心支付！
                            </span>
                        </div>
                        <br>
                        <div class="pv-button" id="pv-button-submitPay">
                            <input value="立即支付" id="paySubmit" class="ui-button ui-button-XL disable"  type="submit">
                            <input autocomplete="off" name="remainShouldPayAmount" id="remainShouldPayAmount"
                            value="134.00" type="hidden">
                           
                            <br>
                        </div>

                        <div class="paySuceess"> 
                            支付成功！
                            <br>
                            <a href="#">查看订单</a>
                            <a href="#">前往首页</a>
                        </div>

                    </div>
                    
                </div>

            </div>
        </div>
       
        
        
    </body>

</html>