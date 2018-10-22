$(function() {
	
	// タブ設定
	setup_tabs();
	
	// コンボボックス変更で検索開始
	$('#searchTarget').change(function() {
		if ( $('#searchTarget').val() != "＜選択してください＞" ) {
			send_ajax();
		}
	});
	
	// タブ操作のスクリプト
	function setup_tabs () {
		$("#C0101_tab_group section").hide();
		$($("#C0101_tab a.d-menu-active").attr("href")).show();
		
		$("#C0101_tab li a").click(function () {
			$("#C0101_tab li a").removeClass("d-menu-active");
			$(this).addClass("d-menu-active");
			$("#C0101_tab_group section").hide();
			$($(this).attr("href")).fadeIn();
			return false;
		});
	}

	// Ajax通信処理
	function send_ajax () {
		$.ajax('/C0101/search', {
			type: 'get',
			dataType: 'html',
			data: { 
				searchTarget: $('#searchTarget').val(), 
				userId: $('#userId').val()
			}
		})
		// 検索成功
		.done(function(data) {
			$('#C0101_resultBox').html(data);
		})
		// 検索失敗
		.fail(function() {
			alert('検索に失敗しました。');
		});
	}
});