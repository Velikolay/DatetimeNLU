parser grammar DateParser;

options {
  tokenVocab=DateLexer;
}

import NumericRules;

parse
  : relative_date | specific_date
  ;

specific_date
  : (month_day_year | spelled_month_day | spelled_month | format_date | full_year)
  ;

relative_date
  : (relative_weekday | relative_days_from_now | relative_neighbour_date | relative_neighbour_week | relative_neighbour_year)
  ;

month_day_year
  : (format_month_day | spelled_month_day | spelled_month) (WHITE_SPACE? (COMMA | DASH) WHITE_SPACE? | WHITE_SPACE ) parse_year
  | parse_year (WHITE_SPACE? (COMMA | DASH) WHITE_SPACE? | WHITE_SPACE ) (format_month_day | spelled_month_day | spelled_month)
  ;

format_date
  : (format_full_date | format_month_day)
  ;

format_full_date
  : day_number DOT month_number DOT norel_year
  | day_number DASH month_number DASH norel_year
  | day_number SLASH month_number SLASH norel_year
  | month_number DOT day_number DOT norel_year
  | month_number DASH day_number DASH norel_year
  | month_number SLASH day_number SLASH norel_year
  ;

format_month_day
  : day_number (DOT | DASH | SLASH) month_number
  | month_number (DOT | DASH | SLASH) day_number
  ;

month_number
  : int_01_to_12_optional_prefix
  ;

day_number
  : int_01_to_31_optional_prefix
  ;

short_year
  : int_00_to_99_mandatory_prefix
  ;

full_year
  : int_four_digits
  ;

norel_year
  : short_year
  | full_year
  ;

parse_year
  : full_year
  | relative_neighbour_year
  ;

relative_neighbour_year
  : NEXT WHITE_SPACE YEAR # next_year
  | LAST WHITE_SPACE YEAR # last_year
  | THIS WHITE_SPACE YEAR # this_year
  ;

relative_neighbour_week
  : NEXT WHITE_SPACE WEEK # next_week
  | (LAST | PREVIOUS | PAST) WHITE_SPACE WEEK # last_week
  | (THIS | THAT) WHITE_SPACE WEEK # this_week
  ;

spelled_month_day
  : spelled_month WHITE_SPACE spelled_or_ordinal_or_int_01_to_31_optional_prefix
  | spelled_or_ordinal_or_int_01_to_31_optional_prefix WHITE_SPACE spelled_month
  | spelled_or_ordinal_or_int_01_to_31_optional_prefix WHITE_SPACE OF WHITE_SPACE spelled_month
  ;

relative_neighbour_date
  : TODAY       # today
  | TOMORROW    # tomorrow
  | YESTERDAY   # yesterday
  ;

relative_days_from_now
  : (IN WHITE_SPACE)? spelled_or_int_01_to_31_optional_prefix WHITE_SPACE DAY (WHITE_SPACE FROM WHITE_SPACE NOW)?
  ;

relative_weekday
  : ((NEXT | ON) WHITE_SPACE)? day_of_week                      # next_day_of_week
  | (LAST | PREVIOUS | PAST) WHITE_SPACE day_of_week            # previous_day_of_week
  | (THIS | THAT) WHITE_SPACE day_of_week                       # this_day_of_week
  | day_of_week WHITE_SPACE relative_neighbour_week             # day_of_neighbour_week
  ;

day_of_week returns [java.time.DayOfWeek dow]
  : SUNDAY      {$dow = java.time.DayOfWeek.SUNDAY;}
  | MONDAY      {$dow = java.time.DayOfWeek.MONDAY;}
  | TUESDAY     {$dow = java.time.DayOfWeek.TUESDAY;}
  | WEDNESDAY   {$dow = java.time.DayOfWeek.WEDNESDAY;}
  | THURSDAY    {$dow = java.time.DayOfWeek.THURSDAY;}
  | FRIDAY      {$dow = java.time.DayOfWeek.FRIDAY;}
  | SATURDAY    {$dow = java.time.DayOfWeek.SATURDAY;}
  ;

spelled_month returns [java.time.Month mnt]
  : JANUARY     {$mnt = java.time.Month.JANUARY;}
  | FEBRUARY    {$mnt = java.time.Month.FEBRUARY;}
  | MARCH       {$mnt = java.time.Month.MARCH;}
  | APRIL       {$mnt = java.time.Month.APRIL;}
  | MAY         {$mnt = java.time.Month.MAY;}
  | JUNE        {$mnt = java.time.Month.JUNE;}
  | JULY        {$mnt = java.time.Month.JULY;}
  | AUGUST      {$mnt = java.time.Month.AUGUST;}
  | SEPTEMBER   {$mnt = java.time.Month.SEPTEMBER;}
  | OCTOBER     {$mnt = java.time.Month.OCTOBER;}
  | NOVEMBER    {$mnt = java.time.Month.NOVEMBER;}
  | DECEMBER    {$mnt = java.time.Month.DECEMBER;}
  ;

  
