$.mReady(function() {

	/* ここから 検索条件 */
	var searchFormControl = function(searchForm) {

		/* ここから イベントのバインド */

		// 検索ボタン押下時
		searchForm.find('.searchBtn').click(function () {
			location.href = '//' + location.host + $.makeSearchUrl(decodeURIComponent([searchForm.attr('action'),searchForm.serialize()].join('?')));
			return false;
		}).end()

		// フリーワードでEnterボタンの押下時
		.find('.freeword').bind('keypress', function(event) {
			if ((event.which && event.which === 13) || (event.keyCode && event.keyCode === 13)) {
				location.href = '//' + location.host + $.makeSearchUrl(decodeURIComponent([searchForm.attr('action'),searchForm.serialize()].join('?')));
				return false;
			} else {
				return true;
			}
		}).end();

	};
	/* ここまで 検索条件 */
	searchFormControl($('#searchFormTop'));
	
	// サジェスト
	$.suggest.bind({
		inputBox : $('#searchFreewordSub'),
		list	 : $('#freewordSuggestSub'),
		form	 : $('#searchFormSub'),
		searchBtn: $('#searchButtonSub'),}
	);
	
	// 検索ボタンクリック時
	$.freewordMenuGa({
		targetform : $('#searchFormSub'),
		targetbutton: $('#searchButtonSub'),
		targetinput: $('#searchFreewordSub'),
		gacategory: "フリーワード検索-PC404エラー"
	});
});
