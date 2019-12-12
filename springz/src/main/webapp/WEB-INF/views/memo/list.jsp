<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<%@ include file="../includes/header.jsp" %>
<script>
	//버튼 누르면 등록하기로 간다
	$(function(){
		  // result 값을 저장 - 등록, 수정, 삭제한 경우
	      var result = '<c:out value="${result}"/>';
	      
	      //result 값이 있는지 확인하는 함수 호출
	      checkModal(result);
	      
	      //뒤로가기 할때 발생하는 오류잡기
	      history.replaceState({}, null, null);
	      
	      //result 값이 있는지 확인하는 함수
	      function checkModal(result){
	    	  if(result === '' || history.state){
	    		  return;
	    	  }if(parseInt(result) > 0){
	    		  $(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록되었습니다.");
	    	  }
	    	  console.log(result);
	    	  $('#myModal').modal('show');
	      }
	      
		  $("#regBtn").click(function(){
		   		self.location = '/memo/register';
		  });
		  
		//페이지 번호 링트 처리
		  $(".paginate_button a").on("click", function(e) {
				 e.preventDefault(); //a태그라서 동작안되게 막아줌
				 $('#pageNum').val($(this).attr('href'));	//내가 누른 a태그의 href값을 $('#pageNum')에 넣어줌
				 $('#actionForm').submit();
		  });
		  
		  //게시물 조회 링크처리
		  $(".move").on("click", function(e) {
			 	 e.preventDefault();
			 	 $("#actionForm").append("<input type='hidden' name='no' value='"+ $(this).attr("href")+"'>");
			 	 $("#actionForm").attr("action", "/memo/get");
			 	 $("#actionForm").submit();
		  });
		  
// 		  $("#f").removeAttr("onclick");
// 		  $("#f").attr("onclick", functionAddBtn('N')); //n없어도됨

// 		  $('a').attr('onclick', $(this).attr('onclick').replace('1', '2'));

		//검색 버튼 이벤트 처리
		  	//검색 조건을 지정하지 않은경우
		  		//'검색 종류를 선택하세요' 메시지 출력
		  	//검색어를 입력하지 않은경우
		  		//'키워드를 입력하세요' 출력
		  var searchForm = $("#searchForm");
		  
		  $("#searchForm button").on("click", function(e) {
			 if(!searchForm.find("option:selected").val()){	//검색 조건을 지정안했을때
				 alert('검색종류를 선택하세요');
				 return false;
			 } 
			 if(!searchForm.find("input[name='keyword']").val()){	//키워드를 입력하지 않았을때
				 alert('키워드를 선택하세요');
				 return false;
			 }
			 //검색 결과 페이지 번호가 1이 되도록 처리
			 searchForm.find("input[name='pageNum']").val("1");
			 e.preventDefault();
			 
			 searchForm.submit();
		  });
		  
		  
	});
</script>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">메모 리스트</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                	메모리스트
                <button id="regBtn" type="button" class="btn btn-xs pull-right">메모 작성하러가기</button>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
            	<table class="table table-striped table-bordered table-hover">
<!--                 <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>내용</th>
                            <th>아이디</th>
                            <th>작성일</th>
<!--                             <th>수정일</th> -->
                            <th>삭제</th>
                            <th>수정</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${memo }" var="memo">
<%--                         <tr class="odd gradeX" onclick="location.href='/memo/get?no=${memo.no }'"> --%>
                        <tr class="odd gradeX">
                            <td>${memo.no }</td>
                            
                            <!-- 제목이 길면 ... 표시 -->
                            <c:choose>
						        <c:when test="${fn:length(memo.title) gt 11}">
						        	<td><a href="${memo.no }" class="move"><c:out value="${fn:substring(memo.title, 0, 10)}"/>......... 
						        	<c:choose>
										<c:when test="${memo.replyCnt > 0}">
											<b>[${memo.replyCnt }]</b> </c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
						        	</a></td>
						        </c:when>
						        <c:otherwise>
						       		<td><a href="${memo.no }" class="move"><c:out value="${memo.title}"/>
						       		<c:choose>
										<c:when test="${memo.replyCnt > 0}">
											<b>[${memo.replyCnt }]</b> </c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									</a></td>
						        </c:otherwise>
							</c:choose>
							
                            <td>${memo.id }</td>
                            <td class="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${memo.regdate }"/></td>
<%--                             <td class="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${memo.updateDate }"/></td> --%>
                            <td><a href="/memo/remove?no=${memo.no }">삭제하기</a></td>
                            <td><a href="/memo/modify?no=${memo.no }">수정하기</a></td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <!-- 검색 조건 및 키워드 입력 부분 -->
                <div class='row'>
                   <div class="col-lg-12">
                   <!--  -->
                      <form id="searchForm" action="/memo/list" method="get">
                         <select name='type'>
                               <option value="" <c:out value="${pageMaker.cri.type ==null?'selected':'' }"/>> 검색 조건 지정 </option>
                               <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }"/>>제목</option>
                               <option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }"/>>내용</option>
                               <option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }"/>>작성자</option>
                               <option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }"/>>제목 or 내용</option>
                               <option value="TW" <c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }"/>>제목 or 작성자</option>
                               <option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC'?'selected':'' }"/>>제목 or 내용 or 작성자</option>
                         </select>
                         <input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword }"/>'>
                         <input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum }'>
                         <input type='hidden' name='amount' value='${pageMaker.cri.amount }'>
                         <button class='btn btn-default'>Search</button>
                      </form>
                   </div>
                </div>
                <!-- END 검색 조건 및 키워드 입력부분 -->
                
                <div class="pull-right">
	              <ul class="pagination">
	                 <c:if test="${pageMaker.prev }">
	                    <li class="paginate_button previous">
	                       <a class="page-link" href="${pageMaker.startPage -1 }">Previous</a></li>
	                 </c:if>
	                 <c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
	                    <li class='paginate_button ${pageMaker.cri.pageNum == num ? "active":"" }'>
	                       <a class="page-link" href="${num}">${num }</a></li>
	                 </c:forEach>
	                 <c:if test="${pageMaker.next }">
	                    <li class="paginate_button next">
	                       <a class="page-link" href="${pageMaker.endPage + 1 }">Next</a></li>
	                 </c:if>
	              </ul>
	           </div>
	           
	            <!-- 실제 클릭하면 동작하는 부분 처리 -->
	           <form id="actionForm" action="/memo/list" method="get">
	           		<input type="hidden" id="pageNum" name="pageNum" value="${pageMaker.cri.pageNum }">
	           		<input type="hidden" id="amount" name="amount" value="${pageMaker.cri.amount }">
	           		<!--  검색 조건과 키워드 파라미터 추가 -->
	           		<input type="hidden" id="type" name="type" value='<c:out value="${pageMaker.cri.type }"/>'>
	           		<input type="hidden" id="keyword" name="keyword" value='<c:out value="${pageMaker.cri.keyword }"/>'>
	           </form>
                
                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                            </div>
                            <div class="modal-body">
                                	처리가 완료되었습니다.
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>







