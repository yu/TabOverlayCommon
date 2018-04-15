package de.codecrafter47.taboverlay.config.template;

import de.codecrafter47.data.api.DataKey;
import de.codecrafter47.taboverlay.Icon;
import de.codecrafter47.taboverlay.config.ErrorHandler;
import de.codecrafter47.taboverlay.config.dsl.CustomPlaceholderConfiguration;
import de.codecrafter47.taboverlay.config.dsl.components.BasicComponentConfiguration;
import de.codecrafter47.taboverlay.config.expression.ExpressionEngine;
import de.codecrafter47.taboverlay.config.icon.IconManager;
import de.codecrafter47.taboverlay.config.placeholder.AbstractPlayerPlaceholderResolver;
import de.codecrafter47.taboverlay.config.placeholder.PlaceholderResolver;
import de.codecrafter47.taboverlay.config.placeholder.PlaceholderResolverChain;
import de.codecrafter47.taboverlay.config.player.PlayerSetFactory;
import de.codecrafter47.taboverlay.config.template.component.BasicComponentTemplate;
import de.codecrafter47.taboverlay.config.template.component.ComponentTemplate;
import de.codecrafter47.taboverlay.config.template.component.ListComponentTemplate;
import de.codecrafter47.taboverlay.config.template.icon.IconTemplate;
import de.codecrafter47.taboverlay.config.template.ping.PingTemplate;
import de.codecrafter47.taboverlay.config.template.text.TextTemplate;
import de.codecrafter47.taboverlay.util.Unchecked;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

@Data
public class TemplateCreationContext implements Cloneable {

    private final ExpressionEngine expressionEngine;

    private final IconManager iconManager;

    private final DataKey<Icon> playerIconDataKey;

    private final DataKey<Integer> playerPingDataKey;

    private final ErrorHandler errorHandler;

    private Map<String, CustomPlaceholderConfiguration> customPlaceholders;

    private Map<String, PlayerSetTemplate> playerSets;

    private TextTemplate defaultText;

    private PingTemplate defaultPing;

    private IconTemplate defaultIcon;

    private PlaceholderResolverChain placeholderResolverChain;

    private AbstractPlayerPlaceholderResolver playerPlaceholderResolver;

    private boolean viewerAvailable = false;

    private boolean playerAvailable = false;

    private int columns = -1;

    // used for recursion detection
    private HashSet<String> visitedCustomPlaceholders = new HashSet<>();

    public OptionalInt getColumns() {
        return columns != -1 ? OptionalInt.of(columns) : OptionalInt.empty();
    }

    public ComponentTemplate emptySlot() {
        return BasicComponentTemplate.builder()
                .icon(defaultIcon)
                .text(defaultText)
                .ping(defaultPing)
                .alignment(BasicComponentConfiguration.Alignment.LEFT)
                .build();
    }

    public ComponentTemplate emptyComponent() {
        // TODO optimize
        return ListComponentTemplate.builder()
                .components(Collections.emptyList())
                .columns(this.getColumns().orElse(1))
                .defaultIcon(this.getDefaultIcon())
                .defaultText(this.getDefaultText())
                .defaultPing(this.getDefaultPing())
                .build();
    }

    public void addPlaceholderResolver(@Nonnull @NonNull PlaceholderResolver resolver) {
        this.placeholderResolverChain = placeholderResolverChain.clone();
        this.placeholderResolverChain.addResolver(resolver);
    }

    public boolean hasVisitedCustomPlaceholder(@Nonnull @NonNull String id) {
        return this.visitedCustomPlaceholders.contains(id);
    }

    public void visitCustomPlaceholder(@Nonnull @NonNull String id) {
        this.visitedCustomPlaceholders = Unchecked.cast(this.visitedCustomPlaceholders.clone());
        this.visitedCustomPlaceholders.add(id);
    }

    @Override
    public TemplateCreationContext clone() {
        TemplateCreationContext clone;
        try {
            clone = (TemplateCreationContext) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
        return clone;
    }
}
