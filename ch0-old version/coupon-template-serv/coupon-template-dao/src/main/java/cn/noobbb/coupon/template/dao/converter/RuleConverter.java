package cn.noobbb.coupon.template.dao.converter;

import cn.noobbb.coupon.template.api.beans.rules.TemplateRule;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RuleConverter implements AttributeConverter<TemplateRule, String> {

    @Autowired
    private Gson gson;

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return gson.toJson(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return gson.fromJson(rule, TemplateRule.class);
    }
}