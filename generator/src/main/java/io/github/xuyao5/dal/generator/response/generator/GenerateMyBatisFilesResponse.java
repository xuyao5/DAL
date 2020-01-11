package io.github.xuyao5.dal.generator.response.generator;

import io.github.xuyao5.dal.generator.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data(staticConstructor = "of")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class GenerateMyBatisFilesResponse extends BaseResponse {
}
