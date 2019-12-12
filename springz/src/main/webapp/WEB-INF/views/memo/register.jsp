<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../includes/header.jsp" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">
        	메모 작성
        </h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
             	   메모 작성
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
            	<form role="form" action="/memo/register" method="post">
            		
            		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
            		<div class="form-group">
            			<label>Title</label>
            			<input class="form-control" name="title">
            		</div>
            		<div class="form-group">
            			<label>content</label>
            			<textarea class="form-control" rows="3" name="content"></textarea>
            		</div>
            		<div class="form-group">
            			<label>id</label>
            			<input class="form-control" name="id" 
            			value='<sec:authentication property="principal.username"/>' readonly="readonly">
            		</div>
            		<button type="submit" class="btn btn-default">작성하기</button>
                    <button type="reset" class="btn btn-default">다시쓰기</button>
            	</form>
            </div>
        </div>
        
        <div class="bigPictureWrapper">
			<div class="bigPicture">
			</div>
		</div>
	
        <div class="row">
           	<div class="col-lg-12">
           		<div class="panel panel-default">
           			<div class="panel-heading">파일첨부</div>
           			<div class="panel-body">
           				<div class="form-group uploadDiv">
           					<input type="file" name="uploadFile" multiple="multiple">
           				</div>
           				<div class="uploadResult">
           					<ul>
           					</ul>
           				</div>
           			</div>
           		</div>
           	</div>
        </div>
        
    </div>
</div>

<script>

$(function(){
	var formObj = $("form[role='form']");
	
	$("button[type='submit']").on("click",function(e){
		e.preventDefault(); //submit 처리 취소
		console.log("submit clicked");
		
		var str = "";
		
		$(".uploadResult ul li").each(function(i, obj){
			var jobj = $(obj);
			
			//BoardAttachVO에 정보를 넣어준다
			str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>"
			+ "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>"
			+ "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>"
			+ "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>"
			
		});
		formObj.append(str).submit(); //취소한 submit 다시 submit 전송
	});
	
	
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
	
	var csrfHeaderName = "${_csrf.headerName}";	//CSRF 토큰 관련 변수 추가
	var csrfTokenValue = "${_csrf.token}";	//CSRF
	
	//첨부 파일 클릭 이벤트 처리
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
	
	//x표시 클릭이벤트처리 삭제 -> x표시 버튼이다
	$(".uploadResult").on("click", "button", function(e){
		console.log("delete file");
		
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		
		//삭제시 이미지 제거할려고
		var targetLi = $(this).closest("li");
		
		$.ajax({
			url : '/deleteFile',
			data : {fileName: targetFile, type: type },
			dataType : 'text',
			type : 'POST',
			beforeSend : function(xhr){	//전송 전 추가 헤더 설정
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success : function(result){
				alert(result);
				targetLi.remove();
			},
			error : function(error){
				alert(error);
			}
		});
	});
	
	
	
	
});
</script>

<%@ include file="../includes/footer.jsp" %>