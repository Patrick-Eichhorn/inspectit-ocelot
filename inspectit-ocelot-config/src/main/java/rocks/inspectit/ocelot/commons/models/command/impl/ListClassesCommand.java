package rocks.inspectit.ocelot.commons.models.command.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rocks.inspectit.ocelot.commons.models.command.Command;

/**
 * Command for requesting a list of available classes and methods.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListClassesCommand extends Command {

    /**
     * Filter query to filter the resulting class set.
     */
    private String filter;

}
