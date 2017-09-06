$(function() {
	loadPropList();
});

//加载道具列表
function loadPropList() {
	$("#propPage").createPage({
		url: "/prop/queryPropList",
		data: {},
		pageSize: 20,
		successFn: function(response) {
			if (response.status == 1) {
                modelWindow(response.msssage)
				return;
			}
			var propList = response.data.propList || [];
			
			var propTrArray = [];
			
			$.each(propList, function(index, item) {
				propTrArray.push("<tr>");
				propTrArray.push("		<td>"+ (index + 1) +"</td>");
				propTrArray.push("		<td><a class='text-primary' href='javascript:showPropViewList(\""+ item.id +"\")'>"+ item.name +"</a></td>");
				propTrArray.push("		<td>"+ item.roundCount +"</td>");
				propTrArray.push("		<td>"+ filterNull(item.firstRound) +"</td>");
				propTrArray.push("</tr>");
			});
			
			$("#propListTbody").empty();
			$("#propListTbody").html(propTrArray.join(''));
		}
	});
}

//显示道具场景表
function showPropViewList(id) {
	
	$("#propRoundPage").createPage({
		url: "/playRound/queryRoundList",
		data: {propId: id},
        contentType: 'application/json;charset=utf-8',
		pageSize: 10,
		successFn: function(response) {
			if (response.status == 1) {
                modelWindow(response.msssage)
				return;
			}
			
			var roundList = response.data.roundList;
			var roundTrArray = [];
			$.each(roundList, function(index, item) {
				roundTrArray.push("<tr>");
				roundTrArray.push("	<td width='5%'>"+ item.seriesNo + "-" + item.roundNo +"</td>");
				roundTrArray.push("	<td width='5%' class='over-hide'>"+ filterNull(item.atmosphere) +"</td>");
				roundTrArray.push("	<td width='5%' class='over-hide'>"+ filterNull(item.site) +"</td>");
				roundTrArray.push("	<td width='15%' class='over-hide' title='"+ filterNull(item.firstLocation) +"'>"+ filterNull(item.firstLocation) +"</td>");
				roundTrArray.push("	<td width='5%' class='over-hide'>"+filterNull(item.pageCount)+"</td>");
				roundTrArray.push("	<td width='20%' class='over-hide' title='"+ filterNull(item.majorRoleNameList.join("|")) +"'>"+ filterNull(item.majorRoleNameList.join("|")) +"</td>");
				roundTrArray.push("	<td width='10%' class='over-hide' title='"+ filterNull(item.guestRoleNameList.join("|")) +"'>"+ filterNull(item.guestRoleNameList.join("|")) +"</td>");
				roundTrArray.push("	<td width='10%' class='over-hide' title='"+ filterNull(item.massRoleNameList.join("|")) +"'>"+ filterNull(item.massRoleNameList.join("|")) +"</td>");
				roundTrArray.push("	<td width='20%' class='over-hide' title='"+ filterNull(item.propNameList.join("|")) +"'>"+ filterNull(item.propNameList.join("|")) +"</td>");
				roundTrArray.push("	<td width='20%' class='over-hide'></td>");
				roundTrArray.push("	<td width='10%' class='over-hide' title='"+ filterNull(item.remark) +"'>"+ filterNull(item.remark) +"</td>");
				roundTrArray.push("</tr>");
				
			});
			
			$("#viewListTbody").empty();
			$("#viewListTbody").html(roundTrArray.join(''));
			
			$('#viewListDiv').modal('show');
		}
	});
	
	//加载统计信息
	var url = "/playRound/queryRoundStatistic";
	var successFn = function(response) {
		if (response.status == 1) {
            modelWindow(response.msssage)
			return ;
		}
		
		//统计信息
		var totalCount = response.data.totalCount || 0;
		var innerCount = response.data.innerCount || 0;
		var outCount = response.data.outCount || 0;
		$("#viewStatistic").html("共" + totalCount + "场，其中内景" + innerCount + "场，外景" + outCount + "场");
	};
	doPost(url, {propId: id}, successFn);
}

//关闭弹窗
function closeWin() {
	$("#roleViewListDiv").hide();
}