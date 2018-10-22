$(function() {
		
	// 画像(BLOBデータ)
	var blob = null;
	
	// 参照ボタンよりファイルを選択後に実行
	$('#uploadFile').change(function(e) {
		
		// ボタン押下 不可
		document.getElementById('upload').disabled = "disabled";
		
		// ファイル情報を取得
		var fileData = e.target.files[0];
		console.log(fileData); // 取得した内容の確認用

		// 画像ファイル以外は処理を止める
		if(!fileData.type.match('image.*')) {
			alert('画像を選択してください。');
			return;
		}

		// FileReaderオブジェクトを使ってファイル読み込み
		var reader = new FileReader();
		
		// ファイル読み込みに成功したときの処理
		reader.onload = function() {
			var upload_original = document.getElementById('upload_original');
			upload_original.src = reader.result;
			
			var canvas = document.getElementById("canvas");
			var ctx = canvas.getContext("2d");
			
			//画像が読み込み終わったら実行
			upload_original.onload = function() {
				
				//画像の元の幅を取得
				var	imgWidth = upload_original.width;
				
				//画像の元の高さを取得
				var imgHeight = upload_original.height;
				
				//画像の比率を取得
				var imgRate = imgWidth / imgHeight;
				
				//Canvas上での画像の位置を初期化
				var imgPos = 0;
				
				//画像が横長のとき
				if(imgRate >= 1){
					//横方向の画像位置を計算
					imgPos = (200 - (200 * imgRate)) / 2; 
					ctx.drawImage(upload_original, imgPos, 0, 200 * imgRate, 200);
				}else{
					//画像が縦長のとき
					//縦方向の画像位置を計算
					imgPos = (200 - (200 / imgRate)) / 2;
					ctx.drawImage(upload_original, 0, imgPos, 200, 200 / imgRate);
				}
							
				// canvasからbase64画像データを取得
				var base64 = canvas.toDataURL('image/jpeg');
				
				// base64からBlobデータを作成
				var barr, bin, i, len;
				bin = atob(base64.split('base64,')[1]);
				len = bin.length;
				barr = new Uint8Array(len);
				i = 0;
				while (i < len) {
					barr[i] = bin.charCodeAt(i);
					i++;
				}
				blob = new Blob([barr], {type: 'image/jpeg'});
				
				// ボタン押下 有効
				document.getElementById('upload').disabled = "";
				
			}
		}
		// ファイル読み込みを実行
		reader.readAsDataURL(fileData);
	});
	
	// アップロードボタンが押されたら実行
	$('#upload').click(function() {
		
		//　CSRFトークン取得
		var token = document.getElementById('_csrf').value;
		
		// ヘッダーにCSRFトークンを設定
		$( document ).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader('X-CSRF-Token', token);
		});
		
		// 加工後のイメージを指定
		var formData = new FormData();
		formData.append('multipartFile', blob);
		
		$.ajax({
			url 		: '/C0902/upload',
			method 		: 'post',
			data 		: formData,
			dataType 	: 'text',
			async 		: false,	// 同期通信
			processData : false,
			contentType : false,
			cache 		: false
		})
		// 成功
		.done(function() {
			alert('アイコンを更新しました。');			
		})
		// 失敗
		.fail(function() {
			alert('アップロードに失敗しました。');
		});
		
		// 画面遷移
		window.location.href = "/C0902";
	});
	
});
