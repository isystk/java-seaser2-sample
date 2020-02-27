-- 本番から取得したデータから個人情報カラムをマスキングして開発機用として利用する。

-- 本番からデータ取得するSQL。取得不要テーブルは除外
--		mysqldump -uroot --password=password -h localhost --port=3306 sample > ./dump.sql
--	取り込むコマンド
--		mysql -uroot -p --default-character-set=utf8mb4 sample < mask.sql

SET FOREIGN_KEY_CHECKS=1;

update t_user set
	MYPO_OPEN_ID_URL='',
	MAIL='iseyoshitaka+test@isystk.com',
	PASSWORD=PASSWORD('11111111'),
	FAMILY_NAME='アカウント姓',
	NAME='アカウント名',
	FAMILY_NAME_KANA='アカウントセイ',
	NAME_KANA="アカウントメイ",
	ZIP='123-4567',
	PREFECTURE_ID='13',
	AREA='文京区',
	ADDRESS='XXXXXXXX',
	BUILDING='isystkビルディング',
	TEL='090-0000-0000';

