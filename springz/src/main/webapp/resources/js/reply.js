console.log("reply Module......");

var replyService = (function(){ 
	
	function add(reply, callback, error){	//댓글 추가  실행하는방법 replyService.add
		console.log("reply add......");
		
		$.ajax({
			type : 'post',						//타입
			url : '/replies/new',				//url 어디로 보내냐
			data : JSON.stringify(reply),		//보내야하는 데이터는 뭐냐
			contentType : "application/json; charset=utf-8",	//데이터 전송타입을 어떤 방식으로 전송할꺼냐
			success : function(result, status, xhr){	//성공했을때 어떻게 처리할꺼냐
				if(callback){
					callback(result);
				}
			},
			error : function(xhr, status, err){			//에러로났을때 어떻게 처리할꺼냐
				if (error){
					error(err);
				}
			}
		})
	}
	
	function getList(param, callback, error){	//댓글 목록보기
		console.log("getList......");
		var no = param.no;
		var page = param.page || 1;
		
		console.log(no);
		
		//다른방법
//		var url = "/replies/pages/" + param.no + "/" +  param.page + ".json";
//		$.getJSON(url. function(result){
//			if(callback){
//				callback(result);
//			}
//		}).fail(function(xhr, status, err){
//			if(error){
//				error();
//			}
//		}
		
		$.getJSON("/replies/pages/" + no + "/" + page + ".json",
			function(data){
				if(callback){
					callback(data.replyCnt, data.list);
//					callback(data);
				}
			}).fail(function(xhr, status, err){
				if(error){
					error();
				}
			});
	}
	
	function remove(rno, replyer, callback, error){	//댓글 삭데
		console.log("remove add......");
		
		 $.ajax({
	         type : 'delete',
	         url : '/replies/'+rno,
	         data : JSON.stringify({rno : rno, replyer : replyer}),
	         contentType : "application/json; charset=utf-8",
	         success : function(deleteResult, status, xhr){
	            if(callback){
	               callback(deleteResult);
	            }
	         },
	         error : function(xhr, status, er){
	            if(error){
	               error(er);
	            }
	         }
	      });
	}
	
	function update(reply, callback, error){	//댓글 수정
		console.log("update add......");
		
		$.ajax({
			type : 'put',						//타입지정
			url : '/replies/' + reply.rno,				//수정할때 reply 넘오옴
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr){	//성공했을때 어떻게 처리할꺼냐
				if(callback){
					callback(result);
				}
			},
			error : function(xhr, status, err){			//에러로났을때 어떻게 처리할꺼냐
				if (error){
					error(err);
				}
			}
		})
	}
	
	function get(rno, callback, error){	//댓글 조회
		console.log("get add......");
		
		$.get("/replies/" + rno + ".json",
			function(result){
				if(callback){
					callback(result);
				}
			}).fail(function(xhr, status, err){
				if(error){
					error();
				}
			});
	}
	
	function displayTime(timeValue){
	      var today = new Date();
	      var gap = today.getTime() - timeValue;
	      var dateObj = new Date(timeValue);
	      var str = "";
	      
	      //오늘 작성한 댓글
	      if(gap < ( 1000*60*60*24 )){
	         var hh = dateObj.getHours();
	         var mi = dateObj.getMinutes();
	         var ss = dateObj.getSeconds();

//	         return [ (hh>9 ? '' : '0') + hh, ' : ', (mi>9 ? '' : '0') + mi, ' : ', (ss>9 ? '' : '0') + ss ].join('');
	         //시 분 초 출력 (앞에 0붙이는거 포함)
	         return (hh>9 ? '' : '0') + hh+ ' : '+ (mi>9 ? '' : '0') + mi+ ' : '+(ss>9 ? '' : '0') + ss;
	      } else {
	         var yy = dateObj.getFullYear();
	         var mm = dateObj.getMonth() + 1;
	         var dd = dateObj.getDate();
	         
//	         return [ yy, '/', (mm>9?'':'0')+mm, '/', (dd>9?'':'0')+dd].join('');
	         //오늘 이전 작성한 댓글은 연월일 출력
	         return yy+ '/'+ (mm>9?'':'0')+mm+ '/'+ (dd>9?'':'0')+dd;
	         // .join : 배열을 문자열로 연결해준다.
	      }
	};
	
	
	return {
		add : add, 
		getList : getList,
		remove : remove,
		update : update,
		get : get,
		displayTime : displayTime
	};
	
	
	
	
})()











