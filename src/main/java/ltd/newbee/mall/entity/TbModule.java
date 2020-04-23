package ltd.newbee.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.Banner;

/**
 * <p>
 * 
 * </p>
 *
 * @author 19012964
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbModule extends Model<TbModule> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 专区名称
     */
    private String modName;
    /**
     * 专区键
     */
    private String modKey;


    /**
     * 专区描述
     */
    private String modDesc;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer modRank;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建者id
     */
    private Integer createUser;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 修改者id
     */
    private Integer updateUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
