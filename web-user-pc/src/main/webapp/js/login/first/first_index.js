$.mReady(function() {

	// リアルタイムチェック
	$.mynaviRealtimeCheck({'textselector': '.realtimecheck', 'url': '/login/first/callRealtimeCheck/'});

	// 自動カナ入力
	$.mynaviAutoKana({'textselector': 'familyName',        'textkanaselector': 'familyNameKana'});
	$.mynaviAutoKana({'textselector': 'name',              'textkanaselector': 'nameKana'});

});
