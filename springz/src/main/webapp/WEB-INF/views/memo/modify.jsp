<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<%@ include file="../includes/header.jsp" %>
<style>
.uploadResult{
	width : 100%;
	background-color: gray;
}
.uploadResult ul{
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}
.uploadResult ul li{
	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}
.uploadResult ul li img{
	width: 100%;
}
.uploadResult ul li span{
	color: white;
}
.bigPictureWrapper{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: gray;
	z-index: 100;
	background: rgba(255,255,255,0.5);
}
.bigPicture{
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}
.bigPicture img{
	width: 600px;
}
</style>

<script>
	//삭제
	$(function(){
// 	      var form = $("form");
	      
// 	      $('button').on("click", function (e){
	    	  
// 	    	 e.preventDefault();	//기본으로 form데이터로 전송하는걸 막는다
	    	 
// 	    	 var oper = $(this).data("oper");
	    	 
// 	    	 if(oper === 'remove'){
// 	    		 form.attr("method", "get")
// 	    		 form.attr("action","/memo/remove");
// 	    	 }else if(oper === 'list'){
// 	    		 form.attr("action","/memo/list").attr("method","get");
	    		 
// 	    		 var pageNumTag = $("input[name='pageNum']").clone();
// 	    		 var amountTag = $("input[name='amount']").clone();
// 	    		 var keywordTag = $("input[name='keyword']").clone();
// 	    		 var typeTag = $("input[name='type']").clone();
	    		 
// 	    		 form.empty();
// 	    		 form.append(pageNumTag);
// 	    		 form.append(amountTag);
// 	    		 form.append(keywordTag);
// 	    		 form.append(typeTag);
	    		 
	    		 
// 	    	 }
// 	    	 form.submit();
// 	      });
	});
</script>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">
        	메모 수정
        </h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <!-- /.panel-heading -->
            <div class="panel-body">
            	<form role="form" action="/memo/modify" method="post">
            		
            		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
            		<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
					<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>'>
	           		<!--  검색 조건과 키워드 파라미터 추가 -->
	           		<input type="hidden" id="type" name="type" value='<c:out value="${cri.type }"/>'>
	           		<input type="hidden" id="keyword" name="keyword" value='<c:out value="${cri.keyword }"/>'>
					
            		<div class="form-group">
            			<label>no</label>
            			<input class="form-control" name="no" readonly="readonly" value='<c:out value="${memo.no }" />'>
            		</div>
            		<div class="form-group">
            			<label>Title</label>
            			<input class="form-control" name="title" value='<c:out value="${memo.title }" />'>
            		</div>
            		<div class="form-group">
            			<label>content</label>
            			<textarea class="form-control" rows="3" name="content" >${memo.content }</textarea>
            		</div>
            		<div class="form-group">
            			<label>id</label>
            			<input class="form-control" name="id" readonly="readonly" value='<c:out value="${memo.id }" />'>
            		</div>
            		
            		<sec:authentication property="principal" var="pinfo"/>
					<sec:authorize access="isAuthenticated()">
						<c:if test="${pinfo.username eq memo.id }">
		            		<button type="submit" data-oper="modify" class="btn btn-default">수정하기</button>
		            		<button type="submit" data-oper="remove" class="btn btn-danger">삭제하기</button>
		            	</c:if>
            		</sec:authorize>
                    <button type="submit" data-oper="list" class="btn btn-info">돌아가기</button>
            	</form>
            </div>
        </div>
    </div>
</div>


<div class="row">
           	<div class="col-lg-12">
           		<div class="panel panel-default">
           			<div class="panel-heading">File Attach</div>
           			<div class="panel-body">
           				<div class="form-group uploadDiv">
		           			<input type="file" name="uploadFile" multiple="multiple">
           				</div>
           				<div class="uploadResult">
           					<ul>
           					</ul>
           				</div>
           				<div class="bigPictureWrapper">
							<div class="bigPicture">
							</div>
						</div>
           			</div>
           		</div>
           	</div>
        </div>

<script>
$(function(){
	var form = $("form");
    
    $('button').on("click", function (e){
  	  
  	 e.preventDefault();	//기본으로 form데이터로 전송하는걸 막는다
  	 
  	 var oper = $(this).data("oper");
  	 
  	 if(oper === 'remove'){
  		 form.attr("method", "get")
  		 form.attr("action","/memo/remove");
  	 }else if(oper === 'list'){
  		 form.attr("action","/memo/list").attr("method","get");
  		 
  		 var pageNumTag = $("input[name='pageNum']").clone();
  		 var amountTag = $("input[name='amount']").clone();
  		 var keywordTag = $("input[name='keyword']").clone();
  		 var typeTag = $("input[name='type']").clone();
  		 
  		 form.empty();
  		 form.append(pageNumTag);
  		 form.append(amountTag);
  		 form.append(keywordTag);
  		 form.append(typeTag);
  		 
  		 
  	 }else if(oper === 'modify'){	//수정
  		 var str = "";
  		 $(".uploadResult ul li").each(function(i, obj){
  			var jobj = $(obj);
  			
  			str += "<input type'hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>"
  			+ "<input type'hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>"
  			+ "<input type'hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>"
  			+ "<input type'hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>"
  		 });
  		 
  		 form.append(str).submit();
  	 }
  	 form.submit();
    });
	
	var csrfHeaderName = "${_csrf.headerName}";	//CSRF 토큰 관련 변수 추가
	var csrfTokenValue = "${_csrf.token}";	//CSRF
	
	
	(function(){
		var no = '<c:out value="${memo.no}"/>';
		
		$.getJSON("/memo/getAttachList", {no: no}, function(result){
			console.log(result);
			var str = "";
			
			$(result).each(function(i, attach){
				//이미지 타입
				if(attach.fileType){
					var fileCallPath = encodeURIComponent( attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
					
					str += "<li data-path='" + attach.uploadPath + "' data-uuid ='" + attach.uuid + "'"
					+ " data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>"
					+ "<div><span>" + attach.fileName + "</span>"
					+ "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image'"
					+ "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
					+ "<img src='/display?fileName=" + fileCallPath + "'>"
					+ "</div></li>"		
				}else{
					str += "<li data-path='" + attach.uploadPath + "' data-uuid ='" + attach.uuid + "'"
					+ " data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>"
					+ "<div><span>" + attach.fileName + "</span><br>"
					+ "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file'"
					+ "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
					+ "<img src='/resources/img/attach.png'>"
					+ "</div></li>"
				}
			});
			$(".uploadResult ul").html(str);
		}).fail(function(xhr, status, err){
			console.log(err);
		});
	})(); //end function
	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB
	
	//첨부 파일 확장자 및 크기 확인 함수
	function checkExtension(fileName, fileSize){
		//파일 크기가 maxSize를 초과하는 경우
		if(fileSize >= maxSize){
			alert('파일 사이즈 초과');
			return false;
		}
		//파일 확장자가 exe, sh, zip, alz인 경우
		if(regex.test(fileName)){
			alert('해당 종류의 파일은 업로드 할 수 없습니다');
			return false;
		}
		return true;
	}//END checkExtension()
	
	$("input[type='file']").change(function(e){
		var formData = new FormData(); //formData는 쉽게말해 가상의 form태그
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		
		if(files.length == 0){
			alert('파일을 선택해주세요');
		}else{
			for(var i=0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				formData.append("uploadFile", files[i]); //formData에 추가
			}
			//formData를 이용해서 필요한 파라미터를 담아서 전송하는 방식
			$.ajax({
				type : 'POST',
				url : '/uploadAjaxAction',
				data : formData,
				dataType : 'json',
				processData : false,
				contentType : false,
				beforeSend : function(xhr){	//전송 전 추가 헤더 설정
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				success : function(result){
					alert("업로드 성공");
					console.log(result);
					showUploadedFile(result);	// 업로된된 파일명 출력
// 					$(".uploadDiv").html(cloneObj.html());	//복제한거 실행 (파일선택 옆에 선택된파일명이 없어짐)
				},
				error : function(error){
					alert('업로드 실패');
// 					$(".uploadDiv").html(cloneObj.html());	//복제한거 실행 (파일선택 옆에 선택된파일명이 없어짐)
				}
			}); //END ajax
		}
	});
	
	function showUploadedFile(uploadResultArr){
// 		uploadResult.html("");	//계속업로드할경우 리셋해서 올린다
		var uploadUL = $(".uploadResult ul");
		var str = "";
		//for문을 돌려야하지만 제이쿼리에서는 each를 쓸수있다
		$(uploadResultArr).each(function(i, obj){
			if(!obj.image){
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
// 				str += "<li><div><a href='/download?fileName=" + fileCallPath + "'>" +
// 				"<img src='/resources/img/attach.png'>" + obj.fileName + "</a>" + 
// 				"<span data-file=\'" + fileCallPath + "\' data-type='file'> x </span></div></li>";

				str += "<li data-path='" + obj.uploadPath + "'"
				+ " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>"
				+ "<div><span>" + obj.fileName + "</span>"
				+ "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file'"
				+ "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
				+ "<img src='/resources/img/attach.png'>" + "</div></li>"; 
				
			}else{
				var fileCallPath = encodeURIComponent( obj.uploadPath + "/s_"+obj.uuid+"_"+obj.fileName);
				var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
				originPath = originPath.replace(new RegExp(/\\/g), "/");
				
				str += "<li data-path='" + obj.uploadPath + "'"
				+ " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'"
				+ "<div><span> " + obj.fileName + "</span>"
				+ "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'>"
				+ "<i class='fa fa-times'></i></button><br>"
				+ "<img src='/display?fileName=" + fileCallPath+"'>" + "</div></li>";
				
			}
		});
		uploadUL.append(str);
	}
	
	$(".uploadResult").on("click", "button", function(e){
		console.log("delete file");
		
		if(confirm("Remove this file? ")){
			var targetLi = $(this).closest("li");
			targetLi.remove();
		}
	});
	
	
	
	
});

</script>

<%@ include file="../includes/footer.jsp" %>