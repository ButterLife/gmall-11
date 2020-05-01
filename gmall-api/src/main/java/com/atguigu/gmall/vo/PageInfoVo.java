package com.atguigu.gmall.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

//有参构造器和无参构造器
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@Data
public class PageInfoVo implements Serializable {
    @ApiModelProperty("总记录数")
    private Long total;
    @ApiModelProperty("总页码")
    private Long totalPage;
    @ApiModelProperty("每页显示的记录数")
    private Long pageSize;
    @ApiModelProperty("分页查询出的数据")
    private List<? extends Object> list;
    @ApiModelProperty("当前页码")
    private Long pageNum;

    /**
     * 封装PageInfoVo的方法
     *
     * @param iPage
     * @param size
     * @return
     */
    public static PageInfoVo getVo(IPage iPage, Integer size) {
        return new PageInfoVo(iPage.getTotal(), iPage.getPages(), size.longValue(), iPage.getRecords(), iPage.getCurrent());
    }

}
