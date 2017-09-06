//题材map
var subjecJson = {};
// subjecJson[] = '全部题材';
subjecJson['1'] = '当代主旋律';
subjecJson['2'] = '武侠';
subjecJson['3'] = '地下斗争';
subjecJson['4'] = '公案';
subjecJson['5'] = '历史正剧';
subjecJson['6'] = '农村';
subjecJson['7'] = '反腐倡廉';
subjecJson['8'] = '反特/谍战';
subjecJson['9'] = '重大革命';
subjecJson['10'] = '历史故事';
subjecJson['11'] = '其他';
subjecJson['12'] = '悬疑';
subjecJson['13'] = '青春';
subjecJson['14'] = '动作';
subjecJson['15'] = '人物传记';
subjecJson['16'] = '军事斗争';
subjecJson['17'] = '少儿';
subjecJson['18'] = '神怪玄幻';
subjecJson['19'] = '社会伦理';
subjecJson['20'] = '时代变迁';
subjecJson['21'] = '奋斗励志';
subjecJson['22'] = '警匪';
subjecJson['23'] = '言情';
subjecJson['24'] = '科幻';
subjecJson['25'] = '商战';
subjecJson['26'] = '戏说演绎';
subjecJson['27'] = '重大历史';
subjecJson['28'] = '战争';
subjecJson['29'] = '都市生活';
subjecJson['30'] = '民间传奇';
subjecJson['31'] = '军旅生活';
subjecJson['32'] = '罪案';
subjecJson['33'] = '当代传奇';
subjecJson['34'] = '近代传奇';
subjecJson['0'] = '未知题材';//这个放到最后
//分类后的题材
var subjectTypeJson = [];
subjectTypeJson.push(['history','历史类',10,5,27,26,15,33,34,30])
subjectTypeJson.push(['live','生活类',21,13,23,29,25,19,6,20])
subjectTypeJson.push(['action','动作类',2,12,14,32,22])
subjectTypeJson.push(['live','战争类',9,16,3,8,28,31])
subjectTypeJson.push(['fantasy','幻想类',24,18])
subjectTypeJson.push(['else','其他类',1,7,17,4,11,0])