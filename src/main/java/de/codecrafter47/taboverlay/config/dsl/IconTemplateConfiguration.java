package de.codecrafter47.taboverlay.config.dsl;

import de.codecrafter47.taboverlay.config.dsl.exception.ConfigurationException;
import de.codecrafter47.taboverlay.config.dsl.yaml.MarkedPropertyBase;
import de.codecrafter47.taboverlay.config.placeholder.PlayerPlaceholder;
import de.codecrafter47.taboverlay.config.template.TemplateCreationContext;
import de.codecrafter47.taboverlay.config.template.icon.IconTemplate;
import de.codecrafter47.taboverlay.config.template.icon.PlayerIconTemplate;

public class IconTemplateConfiguration extends MarkedPropertyBase {

    public static final IconTemplateConfiguration DEFAULT = new IconTemplateConfiguration(TemplateCreationContext::getDefaultIcon);

    private final TemplateConstructor templateConstructor;

    private IconTemplateConfiguration(TemplateConstructor templateConstructor) {
        this.templateConstructor = templateConstructor;
    }

    public IconTemplateConfiguration(String value) {
        this.templateConstructor = tcc -> parse(value, tcc);
    }

    private IconTemplate parse(String value, TemplateCreationContext tcc) {
        if (value.equals("${player skin}")) {
            if (!tcc.isPlayerAvailable()) {
                tcc.getErrorHandler().addWarning("${player skin} cannot be used here", getStartMark());
                return tcc.getDefaultIcon();
            }
            return new PlayerIconTemplate(PlayerPlaceholder.BindPoint.PLAYER, tcc.getPlayerIconDataKey());
        } else if (value.equals("${viewer skin}")) {
            if (!tcc.isViewerAvailable()) {
                tcc.getErrorHandler().addWarning("${viewer skin} cannot be used here", getStartMark());
                return tcc.getDefaultIcon();
            }
            return new PlayerIconTemplate(PlayerPlaceholder.BindPoint.VIEWER, tcc.getPlayerIconDataKey());
        } else {
            return tcc.getIconManager().createIconTemplate(value, tcc);
        }
    }

    public IconTemplate toTemplate(TemplateCreationContext tcc) throws ConfigurationException {
        return templateConstructor.apply(tcc);
    }

    @FunctionalInterface
    private interface TemplateConstructor {

        IconTemplate apply(TemplateCreationContext tcc) throws ConfigurationException;
    }
}
