<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 01-Jun-24
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
<div style="cursor: pointer" onclick="backHandle()">
    <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
    &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Back</span>
</div>
<script>
    function backHandle(){
        console.log(window.history);
        window.history.back();
    }
</script>

