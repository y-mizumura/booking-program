$(function () {
	
	// 参加区分の表示・非表示切り替え
	show_entryKubun();
	
	// 参加・不参加区分変更の度
	$("#entryKubun").change(function() {
		show_entryKubun();
	});
	
	// 参加区分の表示・非表示切り替え
	function show_entryKubun () {
		if ($("#entryKubun").val() == "途中参加") {
			$("#entry_time").css("display", "block");
		} else {
			$("#entry_time").css("display", "none");
		}
	}
});
